package com.tonapps.tonkeeper.core.history

import android.content.Context
import android.util.Log
import androidx.collection.arrayMapOf
import com.tonapps.blockchain.ton.extensions.equalsAddress
import com.tonapps.icu.Coins
import com.tonapps.extensions.max24
import com.tonapps.extensions.short4
import com.tonapps.extensions.withMinus
import com.tonapps.extensions.withPlus
import com.tonapps.icu.CurrencyFormatter
import com.tonapps.tonkeeper.api.fee
import com.tonapps.tonkeeper.api.getNameOrAddress
import com.tonapps.tonkeeper.api.iconURL
import com.tonapps.tonkeeper.api.parsedAmount
import com.tonapps.tonkeeper.api.refund
import com.tonapps.tonkeeper.api.shortAddress
import com.tonapps.tonkeeper.api.title
import com.tonapps.tonkeeper.core.history.list.item.HistoryItem
import io.tonapi.models.AccountAddress
import io.tonapi.models.AccountEvent
import io.tonapi.models.Action
import io.tonapi.models.ActionSimplePreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.tonapps.tonkeeper.extensions.with
import com.tonapps.tonkeeper.helper.DateHelper
import com.tonapps.tonkeeper.ui.screen.dialog.encrypted.EncryptedCommentScreen
import com.tonapps.tonkeeper.usecase.emulation.Emulated
import com.tonapps.uikit.list.ListCell
import com.tonapps.wallet.api.API
import com.tonapps.wallet.api.entity.TokenEntity
import com.tonapps.wallet.data.account.AccountRepository
import com.tonapps.wallet.data.account.entities.WalletEntity
import com.tonapps.wallet.data.collectibles.CollectiblesRepository
import com.tonapps.wallet.data.core.WalletCurrency
import com.tonapps.wallet.data.events.CommentEncryption
import com.tonapps.wallet.data.events.EventsRepository
import com.tonapps.wallet.data.passcode.PasscodeManager
import com.tonapps.wallet.data.rates.RatesRepository
import com.tonapps.wallet.data.rates.entity.RatesEntity
import com.tonapps.wallet.data.settings.SettingsRepository
import com.tonapps.wallet.localization.Localization
import io.tonapi.models.JettonVerificationType
import io.tonapi.models.MessageConsequences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.abs

// TODO request refactoring
class HistoryHelper(
    private val context: Context,
    private val accountRepository: AccountRepository,
    private val ratesRepository: RatesRepository,
    private val collectiblesRepository: CollectiblesRepository,
    private val settingsRepository: SettingsRepository,
    private val eventsRepository: EventsRepository,
    private val passcodeManager: PasscodeManager,
    private val api: API,
) {

    private val currency: WalletCurrency
        get() = settingsRepository.currency

    companion object {

        const val MINUS_SYMBOL = "-"
        const val PLUS_SYMBOL = "+"

        private val monthYearFormatter = SimpleDateFormat("MMMM yyyy", Locale.US)
        private val dayMonthFormatter = SimpleDateFormat("d MMMM", Locale.US)

        private fun getGroupKey(timestamp: Long): String {
            try {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = timestamp * 1000
                val now = Calendar.getInstance()
                val yearDiff = now.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
                val monthDiff = yearDiff * 12 + now.get(Calendar.MONTH) - calendar.get(Calendar.MONTH)

                return if (monthDiff < 1) {
                    dayMonthFormatter.format(calendar.time)
                } else {
                    monthYearFormatter.format(calendar.time)
                }
            } catch (e: Throwable) {
                return "zero"
            }
        }
    }

    private data class ActionDateSection(
        val date: Long,
        val dateFormat: String,
        val events: MutableList<HistoryItem>
    ) {

        fun get(): List<HistoryItem> {
            val list = mutableListOf<HistoryItem>()
            list.add(HistoryItem.Header(dateFormat, date))
            for (event in events) {
                list.add(event)
            }
            return list.toList()
        }
    }

    private fun isBurnAccount(account: AccountAddress): Boolean {
        val burnAddress = api.getBurnAddress()
        if (burnAddress.equalsAddress(account.address) || (account.name != null && burnAddress == account.name)) {
            return true
        }
        return "UQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJKZ".equalsAddress(account.address)
    }

    private fun sort(list: List<HistoryItem>): List<HistoryItem> {
        return list
            .filter { it is HistoryItem.Event || it is HistoryItem.App }
            .distinctBy { it.uniqueId }
            .sortedWith { a, b ->
                (b.timestampForSort - a.timestampForSort).toInt()
            }
    }

    fun groupByDate(items: List<HistoryItem>): List<HistoryItem> {
        val events = sort(items)
        val output = arrayMapOf<String, ActionDateSection>()
        for (event in events) {
            val groupKey = getGroupKey(event.timestampForSort)
            val date = event.timestampForSort
            val section = output[groupKey] ?: ActionDateSection(
                date,
                DateHelper.formatTransactionsGroupDate(
                    context,
                    date,
                    settingsRepository.getLocale()
                ),
                mutableListOf()
            )
            section.events.add(event)
            output[groupKey] = section
        }
        return output.map { it.value.get() }.flatten().sortedWith { a, b ->
            (b.timestampForSort - a.timestampForSort).toInt()
        }.distinctBy { it.uniqueId }.toList()
    }

    fun requestDecryptComment(
        activity: Context,
        comment: HistoryItem.Event.Comment,
        txId: String,
        senderAddress: String,
    ): Flow<HistoryItem.Event.Comment> = accountRepository.selectedWalletFlow.take(1).map {
        if (settingsRepository.showEncryptedCommentModal) {
            val noShowAgain = withContext(Dispatchers.Main) {
                EncryptedCommentScreen.show(activity)
            } ?: throw Exception("User canceled")
            settingsRepository.showEncryptedCommentModal = !noShowAgain
        }
        if (!passcodeManager.confirmation(activity, context.getString(Localization.app_name))) {
            throw Exception("Wrong passcode")
        }
        it
    }.map { wallet ->
        val privateKey = accountRepository.getPrivateKey(wallet.id) ?: throw Exception("Private key not found")
        val decrypted = CommentEncryption.decryptComment(
            wallet.publicKey,
            privateKey,
            comment.body,
            senderAddress
        )
        eventsRepository.saveDecryptedComment(txId, decrypted)
        HistoryItem.Event.Comment(decrypted)
    }.flowOn(Dispatchers.IO)

    data class Details(
        val accountId: String,
        val items: List<HistoryItem>,
        val fee: Coins,
        val feeFormat: CharSequence,
        val feeFiat: Coins,
        val feeFiatFormat: CharSequence
    )

    suspend fun create(
        wallet: WalletEntity,
        response: MessageConsequences,
        rates: RatesEntity,
        isBattery: Boolean = false,
    ): Details {
        val items = mapping(wallet, response.event, true, positionExtra = 1).toMutableList()
        val extra = response.event.extra

        val fee = if (0 > extra) Coins.of(abs(extra)) else Coins.ZERO
        val feeFormat = "≈ " + CurrencyFormatter.format("TON", fee)
        val feeFiat = rates.convert("TON", fee)
        val feeFiatFormat = CurrencyFormatter.formatFiat(rates.currency.code, feeFiat)

        val refund = if (extra > 0) Coins.of(extra) else Coins.ZERO
        val refundFormat = "≈ " + CurrencyFormatter.format("TON", refund)
        val refundFiat = rates.convert("TON", refund)
        val refundFiatFormat = CurrencyFormatter.formatFiat(rates.currency.code, refundFiat)

        val isRefund = extra > 0


        items.add(
            HistoryItem.Event(
                index = items.lastIndex + 1,
                position = ListCell.Position.LAST,
                txId = "fee",
                iconURL = "",
                action = if (isRefund) ActionType.Refund else ActionType.Fee,
                title = "",
                subtitle = if (isBattery) context.getString(Localization.will_be_paid_with_battery) else "",
                value = if (isRefund) refundFormat else feeFormat,
                date = if (isRefund) refundFiatFormat.toString() else feeFiatFormat.toString(),
                isOut = true,
                sender = null,
                recipient = null,
                failed = false,
                isScam = false,
                wallet = wallet,
                isMaybeSpam = false
            )
        )

        return Details(
            accountId = wallet.accountId,
            items = items.toList(),
            fee = fee,
            feeFormat = feeFormat,
            feeFiat = feeFiat,
            feeFiatFormat = feeFiatFormat
        )
    }

    suspend fun create(
        wallet: WalletEntity,
        emulated: Emulated
    ): Details {
        val items = mapping(
            wallet = wallet,
            event = emulated.consequences?.event,
            removeDate = true,
            hiddenBalances = false,
            positionExtra = 1,
            safeMode = false
        ).toMutableList()

        val feeFormat = "≈ " + CurrencyFormatter.format("TON", emulated.extra.value)
        val feeFiatFormat = CurrencyFormatter.formatFiat(emulated.currency.code, emulated.extra.fiat)

        items.add(
            HistoryItem.Event(
                index = items.lastIndex + 1,
                position = ListCell.Position.LAST,
                txId = "fee",
                iconURL = "",
                action = if (emulated.extra.isRefund) ActionType.Refund else ActionType.Fee,
                title = "",
                subtitle = if (emulated.withBattery) context.getString(Localization.will_be_paid_with_battery) else "",
                value = feeFormat,
                date = feeFiatFormat.toString(),
                isOut = true,
                sender = null,
                recipient = null,
                failed = false,
                isScam = false,
                wallet = wallet
            )
        )

        return Details(
            accountId = wallet.accountId,
            items = items.toList(),
            fee = emulated.extra.value,
            feeFormat = feeFormat,
            feeFiat = emulated.extra.fiat,
            feeFiatFormat = feeFiatFormat
        )
    }

    fun transform(loading: Boolean, items: List<HistoryItem>): List<HistoryItem> {
        return if (loading) {
            withLoadingItem(items)
        } else {
            removeLoadingItem(items)
        }
    }

    fun withLoadingItem(items: List<HistoryItem>): List<HistoryItem> {
        val last = items.lastOrNull()
        if (last is HistoryItem.Loader) {
            return items
        }

        val newItems = items.toMutableList()
        newItems.add(HistoryItem.Loader(newItems.size, System.currentTimeMillis() / 1000))
        return newItems
    }

    fun removeLoadingItem(items: List<HistoryItem>): List<HistoryItem> {
        val last = items.lastOrNull()
        if (last is HistoryItem.Loader) {
            val newItems = items.toMutableList()
            newItems.removeAt(newItems.size - 1)
            return newItems
        }
        return items
    }

    suspend fun mapping(
        wallet: WalletEntity,
        event: AccountEvent?,
        removeDate: Boolean = false,
        hiddenBalances: Boolean = false,
        positionExtra: Int = 0,
        safeMode: Boolean = settingsRepository.isSafeModeEnabled()
    ): List<HistoryItem> {
        if (event == null) {
            return createFakeUnknownList()
        }
        return mapping(wallet, listOf(event), removeDate, hiddenBalances, positionExtra, safeMode)
    }

    suspend fun getEvent(
        wallet: WalletEntity,
        eventId: String,
        removeDate: Boolean = false,
        hiddenBalances: Boolean = false
    ): List<HistoryItem> {
        val events = eventsRepository.getSingle(eventId, wallet.testnet) ?: return emptyList()
        return mapping(wallet, events, removeDate, hiddenBalances)
    }

    suspend fun mapping(
        wallet: WalletEntity,
        events: List<AccountEvent>,
        removeDate: Boolean = false,
        hiddenBalances: Boolean = false,
        positionExtra: Int = 0,
        safeMode: Boolean = settingsRepository.isSafeModeEnabled()
    ): List<HistoryItem> = withContext(Dispatchers.IO) {
        val items = mutableListOf<HistoryItem>()
        for (event in events) {
            val pending = event.inProgress

            val actions = event.actions
            val fee = Coins.of(event.fee)
            val refund = Coins.of(event.refund)

            val currency = settingsRepository.currency

            val rates = ratesRepository.getRates(currency, TokenEntity.TON.symbol)
            val feeInCurrency = rates.convert(TokenEntity.TON.symbol, fee)
            val refundInCurrency = rates.convert(TokenEntity.TON.symbol, refund)

            val chunkItems = mutableListOf<HistoryItem>()
            for ((actionIndex, action) in actions.withIndex()) {
                if (safeMode && event.isScam) {
                    continue
                }
                val timestamp = if (removeDate) 0 else event.timestamp
                val isScam = event.isScam || settingsRepository.isSpamTransaction(wallet.id, event.eventId)

                val item = action(
                    index = actionIndex,
                    txId = event.eventId,
                    wallet = wallet,
                    action = action,
                    timestamp = timestamp,
                    isScam = isScam
                ) ?: continue

                chunkItems.add(
                    item.copy(
                        pending = pending,
                        position = ListCell.getPosition(actions.size + positionExtra, actionIndex),
                        fee = if (fee.isPositive) CurrencyFormatter.format(
                            TokenEntity.TON.symbol,
                            fee,
                            TokenEntity.TON.decimals
                        ) else null,
                        feeInCurrency = CurrencyFormatter.formatFiat(currency.code, feeInCurrency),
                        refund = if (refund.isPositive) CurrencyFormatter.format(
                            TokenEntity.TON.symbol,
                            refund,
                            TokenEntity.TON.decimals
                        ) else null,
                        refundInCurrency = CurrencyFormatter.formatFiat(
                            currency.code,
                            refundInCurrency
                        ),
                        lt = event.lt,
                        hiddenBalance = hiddenBalances
                    )
                )
            }

            if (chunkItems.size > 0 && !hasWrongPosition(chunkItems, positionExtra)) {
                items.addAll(chunkItems)
            }
        }

        return@withContext items
    }

    private fun hasWrongPosition(items: List<HistoryItem>, positionExtra: Int): Boolean {
        if (positionExtra > 0) {
            return false
        }
        if (items.isEmpty()) {
            return false
        } else if (items.size == 1) {
            val first = items.first() as? HistoryItem.Event ?: return false
            return first.position != ListCell.Position.SINGLE
        } else {
            val first = items.first() as? HistoryItem.Event ?: return false
            val last = items.last() as? HistoryItem.Event ?: return false
            return first.position != ListCell.Position.FIRST || last.position != ListCell.Position.LAST
        }
    }

    private suspend fun action(
        index: Int,
        txId: String,
        wallet: WalletEntity,
        action: Action,
        timestamp: Long,
        isScam: Boolean,
        safeMode: Boolean = settingsRepository.isSafeModeEnabled()
    ): HistoryItem.Event? {
        val simplePreview = action.simplePreview
        val date = DateHelper.formatTransactionTime(timestamp, settingsRepository.getLocale())
        val dateDetails = DateHelper.formatTransactionDetailsTime(timestamp, settingsRepository.getLocale())

        // actionArgs.isTon && !actionArgs.isOut && !actionArgs.isScam && actionArgs.comment != null
        if (action.jettonSwap != null) {
            val jettonSwap = action.jettonSwap!!
            val tokenIn = jettonSwap.tokenIn
            val tokenOut = jettonSwap.tokenOut

            if ((!tokenIn.verified || !tokenOut.verified) && safeMode) {
                return null
            }

            val amountIn = jettonSwap.amountCoinsIn
            val amountOut = jettonSwap.amountCoinsOut

            val value = CurrencyFormatter.format(tokenOut.symbol, amountOut, 2).withPlus
            val value2 = CurrencyFormatter.format(tokenIn.symbol, amountIn, 2).withMinus

            val rates = ratesRepository.getRates(currency, tokenIn.address)
            val inCurrency = rates.convert(tokenIn.address, amountIn)

            return HistoryItem.Event(
                index = index,
                txId = txId,
                iconURL = "",
                action = ActionType.Swap,
                title = simplePreview.name,
                subtitle = wallet.address.short4,
                value = value,
                value2 = value2,
                coinIconUrl = tokenIn.imageUri.toString(),
                coinIconUrl2 = tokenOut.imageUri.toString(),
                timestamp = timestamp,
                date = date,
                dateDetails = dateDetails,
                isOut = false,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                currency = CurrencyFormatter.formatFiat(currency.code, inCurrency),
                failed = action.status == Action.Status.failed,
                unverifiedToken = !tokenIn.verified || !tokenOut.verified,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.jettonTransfer != null) {
            val jettonTransfer = action.jettonTransfer!!
            val token = jettonTransfer.jetton.address

            if (safeMode && jettonTransfer.jetton.verification != JettonVerificationType.whitelist) {
                return null
            }

            val symbol = jettonTransfer.jetton.symbol
            val isOut = !wallet.isMyAddress(jettonTransfer.recipient?.address ?: "")

            val isBurn = jettonTransfer.recipient?.let {
                isBurnAccount(it)
            } ?: false

            val amount = Coins.ofNano(jettonTransfer.amount, jettonTransfer.jetton.decimals)
            var value = CurrencyFormatter.format(symbol, amount, 2)

            val itemAction: ActionType
            val accountAddress: AccountAddress?

            if (isBurn) {
                itemAction = ActionType.JettonBurn
                accountAddress = jettonTransfer.recipient
                value = value.withMinus
            } else if (isOut || wallet.isMyAddress(jettonTransfer.sender?.address ?: "")) {
                itemAction = ActionType.Send
                accountAddress = jettonTransfer.recipient
                value = value.withMinus
            } else {
                itemAction = ActionType.Received
                accountAddress = jettonTransfer.sender
                value = value.withPlus
            }

            val rates = ratesRepository.getRates(currency, token)
            val inCurrency = rates.convert(token, amount)
            val isEncryptedComment = jettonTransfer.encryptedComment != null

            val comment = HistoryItem.Event.Comment.create(
                jettonTransfer.comment,
                jettonTransfer.encryptedComment,
                if (isEncryptedComment) eventsRepository.getDecryptedComment(txId) else null
            )

            return HistoryItem.Event(
                index = index,
                txId = txId,
                iconURL = accountAddress?.iconURL ?: "",
                action = itemAction,
                title = simplePreview.name,
                subtitle = if (!isBurn) {
                    accountAddress?.getNameOrAddress(wallet.testnet, true) ?: ""
                } else {
                    api.getBurnAddress()
                },
                comment = comment,
                value = value,
                tokenCode = "",
                coinIconUrl = jettonTransfer.jetton.image,
                timestamp = timestamp,
                date = date,
                dateDetails = dateDetails,
                isOut = isOut,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                currency = CurrencyFormatter.formatFiat(currency.code, inCurrency),
                failed = action.status == Action.Status.failed,
                unverifiedToken = jettonTransfer.jetton.verification != JettonVerificationType.whitelist,
                isScam = isScam,
                wallet = wallet,
                isMaybeSpam = action.getTonAmountRaw(ratesRepository) < Coins.of(0.03),
                spamState = settingsRepository.getSpamStateTransaction(wallet.id, txId)
            )
        } else if (action.tonTransfer != null) {
            val tonTransfer = action.tonTransfer!!

            val isOut = !wallet.isMyAddress(tonTransfer.recipient.address)

            val itemAction: ActionType
            val accountAddress: AccountAddress

            val amount = Coins.of(tonTransfer.amount)
            var value = CurrencyFormatter.format("TON", amount, 2)

            if (isOut || wallet.isMyAddress(tonTransfer.sender.address)) {
                itemAction = ActionType.Send
                accountAddress = tonTransfer.recipient
                value = value.withMinus
            } else {
                itemAction = ActionType.Received
                accountAddress = tonTransfer.sender
                value = value.withPlus
            }

            val rates = ratesRepository.getRates(currency, TokenEntity.TON.symbol)
            val inCurrency = rates.convert(TokenEntity.TON.symbol, amount)
            val isEncryptedComment = tonTransfer.encryptedComment != null

            val comment = HistoryItem.Event.Comment.create(
                tonTransfer.comment,
                tonTransfer.encryptedComment,
                if (isEncryptedComment) eventsRepository.getDecryptedComment(txId) else null
            )

            return HistoryItem.Event(
                index = index,
                txId = txId,
                iconURL = accountAddress.iconURL,
                action = itemAction,
                title = simplePreview.name,
                subtitle = accountAddress.getNameOrAddress(wallet.testnet, true),
                comment = comment,
                value = value,
                tokenCode = "TON",
                coinIconUrl = TokenEntity.TON.imageUri.toString(),
                timestamp = timestamp,
                date = date,
                dateDetails = dateDetails,
                isOut = isOut,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                currency = CurrencyFormatter.formatFiat(currency.code, inCurrency),
                failed = action.status == Action.Status.failed,
                isScam = isScam,
                wallet = wallet,
                isMaybeSpam = action.getTonAmountRaw(ratesRepository) < Coins.of(0.03),
                spamState = settingsRepository.getSpamStateTransaction(wallet.id, txId)
            )
        } else if (action.smartContractExec != null) {
            val smartContractExec = action.smartContractExec!!
            val executor = smartContractExec.executor

            val amount = Coins.of(smartContractExec.tonAttached)
            val value = CurrencyFormatter.format("TON", amount, 2)

            return HistoryItem.Event(
                index = index,
                txId = txId,
                iconURL = executor.iconURL,
                action = ActionType.CallContract,
                title = simplePreview.name,
                subtitle = executor.getNameOrAddress(wallet.testnet, true),
                value = value.withMinus,
                tokenCode = "TON",
                timestamp = timestamp,
                date = date,
                dateDetails = dateDetails,
                isOut = true,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.nftItemTransfer != null) {
            val nftItemTransfer = action.nftItemTransfer!!

            val sender = nftItemTransfer.sender
            val recipient = nftItemTransfer.recipient
            val isOut = !wallet.isMyAddress(recipient?.address ?: "-")
            val isBurn = recipient?.let { isBurnAccount(it) } ?: false

            val itemAction: ActionType
            val iconURL: String?
            val subtitle: String

            if (isBurn) {
                itemAction = ActionType.JettonBurn
                iconURL = recipient?.iconURL
                subtitle = api.getBurnAddress()
            } else if (isOut || wallet.isMyAddress(sender?.address ?: "")) {
                itemAction = ActionType.NftSend
                iconURL = recipient?.iconURL
                subtitle = recipient?.getNameOrAddress(wallet.testnet, true) ?: ""
            } else {
                itemAction = ActionType.NftReceived
                iconURL = sender?.iconURL
                subtitle = sender?.getNameOrAddress(wallet.testnet, true) ?: ""
            }

            val nftItem = collectiblesRepository.getNft(
                accountId = wallet.accountId,
                testnet = wallet.testnet,
                address = nftItemTransfer.nft
            )?.let {
                val pref = settingsRepository.getTokenPrefs(wallet.id, it.address)
                it.with(pref)
            }

            if (safeMode && nftItem?.verified != true) {
                return null
            }

            val isEncryptedComment = nftItemTransfer.encryptedComment != null

            val comment = HistoryItem.Event.Comment.create(
                nftItemTransfer.comment,
                nftItemTransfer.encryptedComment,
                if (isEncryptedComment) eventsRepository.getDecryptedComment(txId) else null
            )

            return HistoryItem.Event(
                index = index,
                txId = txId,
                iconURL = iconURL,
                action = itemAction,
                title = simplePreview.name,
                subtitle = subtitle,
                comment = comment,
                value = "NFT",
                nft = nftItem,
                tokenCode = "NFT",
                timestamp = timestamp,
                date = date,
                dateDetails = dateDetails,
                unverifiedToken = nftItem?.verified == false,
                isOut = isOut,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.contractDeploy != null) {
            return HistoryItem.Event(
                index = index,
                txId = txId,
                iconURL = "",
                action = ActionType.DeployContract,
                title = simplePreview.name,
                subtitle = wallet.address.shortAddress,
                value = MINUS_SYMBOL,
                tokenCode = "TON",
                timestamp = timestamp,
                date = date,
                dateDetails = dateDetails,
                isOut = false,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.depositStake != null) {
            val depositStake = action.depositStake!!

            val amount = Coins.of(depositStake.amount)
            val value = CurrencyFormatter.format("TON", amount, 2)

            return HistoryItem.Event(
                index = index,
                iconURL = depositStake.implementation.iconURL,
                txId = txId,
                action = ActionType.DepositStake,
                title = simplePreview.name,
                subtitle = depositStake.pool.getNameOrAddress(wallet.testnet, true),
                value = value.withMinus,
                tokenCode = "TON",
                timestamp = timestamp,
                coinIconUrl = depositStake.implementation.iconURL,
                date = date,
                dateDetails = dateDetails,
                isOut = false,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.jettonMint != null) {
            val jettonMint = action.jettonMint!!

            if (safeMode && jettonMint.jetton.verification != JettonVerificationType.whitelist) {
                return null
            }

            val amount = jettonMint.parsedAmount

            val value = CurrencyFormatter.format(jettonMint.jetton.symbol, amount, 2)

            return HistoryItem.Event(
                index = index,
                txId = txId,
                action = ActionType.JettonMint,
                title = simplePreview.name,
                subtitle = jettonMint.jetton.name,
                value = value.withPlus,
                tokenCode = jettonMint.jetton.symbol,
                timestamp = timestamp,
                coinIconUrl = jettonMint.jetton.image,
                date = date,
                dateDetails = dateDetails,
                isOut = false,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                unverifiedToken = jettonMint.jetton.verification != JettonVerificationType.whitelist,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.withdrawStakeRequest != null) {
            val withdrawStakeRequest = action.withdrawStakeRequest!!

            val amount = Coins.of(withdrawStakeRequest.amount ?: 0L)
            val value = CurrencyFormatter.format("TON", amount, 2)

            return HistoryItem.Event(
                index = index,
                iconURL = withdrawStakeRequest.implementation.iconURL,
                txId = txId,
                action = ActionType.WithdrawStakeRequest,
                title = simplePreview.name,
                subtitle = withdrawStakeRequest.pool.getNameOrAddress(wallet.testnet, true),
                value = value,
                tokenCode = "TON",
                timestamp = timestamp,
                coinIconUrl = withdrawStakeRequest.implementation.iconURL,
                date = date,
                dateDetails = dateDetails,
                isOut = false,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.domainRenew != null) {
            val domainRenew = action.domainRenew!!

            return HistoryItem.Event(
                index = index,
                txId = txId,
                action = ActionType.DomainRenewal,
                title = simplePreview.name,
                subtitle = domainRenew.domain.max24,
                value = MINUS_SYMBOL,
                tokenCode = "",
                timestamp = timestamp,
                date = date,
                dateDetails = dateDetails,
                isOut = false,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.auctionBid != null) {
            val auctionBid = action.auctionBid!!
            val subtitle = auctionBid.nft?.title?.max24 ?: auctionBid.bidder.getNameOrAddress(
                wallet.testnet,
                true
            )

            val amount = Coins.ofNano(auctionBid.amount.value)
            val tokenCode = auctionBid.amount.tokenName

            val value = CurrencyFormatter.format(auctionBid.amount.tokenName, amount, 2)

            return HistoryItem.Event(
                index = index,
                txId = txId,
                action = ActionType.AuctionBid,
                title = simplePreview.name,
                subtitle = subtitle,
                value = value.withMinus,
                tokenCode = tokenCode,
                timestamp = timestamp,
                date = date,
                dateDetails = dateDetails,
                isOut = false,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.type == Action.Type.unknown) {
            return createUnknown(
                index,
                txId,
                action,
                date,
                timestamp,
                simplePreview,
                dateDetails,
                isScam,
                wallet
            )
        } else if (action.withdrawStake != null) {
            val withdrawStake = action.withdrawStake!!

            val amount = Coins.of(withdrawStake.amount)
            val value = CurrencyFormatter.format("TON", amount, 2)

            return HistoryItem.Event(
                index = index,
                txId = txId,
                iconURL = withdrawStake.implementation.iconURL,
                action = ActionType.WithdrawStake,
                title = simplePreview.name,
                subtitle = withdrawStake.pool.getNameOrAddress(wallet.testnet, true),
                value = value.withPlus,
                tokenCode = "TON",
                timestamp = timestamp,
                coinIconUrl = withdrawStake.implementation.iconURL,
                date = date,
                dateDetails = dateDetails,
                isOut = false,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.nftPurchase != null) {
            val nftPurchase = action.nftPurchase!!

            if (safeMode && !nftPurchase.nft.verified) {
                return null
            }

            val amount = Coins.of(nftPurchase.amount.value.toLong())
            val value = CurrencyFormatter.format(nftPurchase.amount.tokenName, amount, 2)

            val nftItem = collectiblesRepository.getNft(
                accountId = wallet.accountId,
                testnet = wallet.testnet,
                address = nftPurchase.nft.address
            )?.let {
                val pref = settingsRepository.getTokenPrefs(wallet.id, it.address)
                it.with(pref)
            }

            return HistoryItem.Event(
                index = index,
                txId = txId,
                action = ActionType.NftPurchase,
                title = simplePreview.name,
                subtitle = nftPurchase.buyer.getNameOrAddress(wallet.testnet, true),
                value = value.withMinus,
                tokenCode = "TON",
                timestamp = timestamp,
                nft = nftItem,
                date = date,
                dateDetails = dateDetails,
                isOut = false,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.jettonBurn != null) {
            val jettonBurn = action.jettonBurn!!

            if (safeMode && jettonBurn.jetton.verification != JettonVerificationType.whitelist) {
                return null
            }

            val amount = jettonBurn.parsedAmount
            val value = CurrencyFormatter.format(jettonBurn.jetton.symbol, amount, 2)

            return HistoryItem.Event(
                index = index,
                txId = txId,
                action = ActionType.JettonBurn,
                title = simplePreview.name,
                subtitle = jettonBurn.sender.getNameOrAddress(wallet.testnet, true),
                value = value.withMinus,
                tokenCode = jettonBurn.jetton.symbol,
                timestamp = timestamp,
                coinIconUrl = jettonBurn.jetton.image,
                date = date,
                dateDetails = dateDetails,
                isOut = false,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                unverifiedToken = jettonBurn.jetton.verification != JettonVerificationType.whitelist,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.unSubscribe != null) {
            val unsubscribe = action.unSubscribe!!

            return HistoryItem.Event(
                index = index,
                txId = txId,
                action = ActionType.UnSubscribe,
                title = simplePreview.name,
                subtitle = unsubscribe.beneficiary.getNameOrAddress(wallet.testnet, true),
                value = MINUS_SYMBOL,
                tokenCode = "TON",
                timestamp = timestamp,
                coinIconUrl = unsubscribe.beneficiary.iconURL ?: "",
                date = date,
                dateDetails = dateDetails,
                isOut = false,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                isScam = isScam,
                wallet = wallet,
            )
        } else if (action.subscribe != null) {
            val subscribe = action.subscribe!!

            val amount = Coins.of(subscribe.amount)
            val value = CurrencyFormatter.format("TON", amount, 2)

            return HistoryItem.Event(
                index = index,
                txId = txId,
                action = ActionType.Subscribe,
                title = simplePreview.name,
                subtitle = subscribe.beneficiary.getNameOrAddress(wallet.testnet, true),
                value = value.withMinus,
                tokenCode = "TON",
                timestamp = timestamp,
                coinIconUrl = subscribe.beneficiary.iconURL ?: "",
                date = date,
                dateDetails = dateDetails,
                isOut = false,
                sender = HistoryItem.Account.ofSender(action, wallet.testnet),
                recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
                failed = action.status == Action.Status.failed,
                isScam = isScam,
                wallet = wallet,
            )
        } else {
            return createUnknown(
                index,
                txId,
                action,
                date,
                timestamp,
                simplePreview,
                dateDetails,
                isScam,
                wallet
            )
        }
    }

    private fun createFakeUnknownList() = listOf(createFakeUnknown())

    private fun createFakeUnknown() = HistoryItem.Event(
        index = 0,
        txId = "",
        action = ActionType.Unknown,
        title = context.getString(Localization.unknown),
        subtitle = context.getString(Localization.unknown_error),
        value = MINUS_SYMBOL,
        tokenCode = "TON",
        timestamp = 0,
        date = "",
        dateDetails = "",
        isOut = false,
        sender = null,
        recipient = null,
        failed = false,
        isScam = false,
        wallet = WalletEntity.EMPTY,
    )

    private fun createUnknown(
        index: Int,
        txId: String,
        action: Action,
        date: String,
        timestamp: Long,
        simplePreview: ActionSimplePreview,
        dateDetails: String,
        isScam: Boolean,
        wallet: WalletEntity,
    ) = HistoryItem.Event(
        index = index,
        txId = txId,
        action = ActionType.Unknown,
        title = simplePreview.name,
        subtitle = action.simplePreview.description.max24,
        value = MINUS_SYMBOL,
        tokenCode = "TON",
        timestamp = timestamp,
        date = date,
        dateDetails = dateDetails,
        isOut = false,
        sender = HistoryItem.Account.ofSender(action, wallet.testnet),
        recipient = HistoryItem.Account.ofRecipient(action, wallet.testnet),
        failed = action.status == Action.Status.failed,
        isScam = isScam,
        wallet = wallet,
    )
}