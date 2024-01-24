package com.tonkeeper.fragment.jetton

import com.tonkeeper.api.parsedBalance
import com.tonkeeper.core.history.list.item.HistoryItem
import com.tonkeeper.fragment.jetton.list.JettonItem
import core.formatter.CurrencyFormatter
import io.tonapi.models.JettonBalance
import ton.wallet.WalletType
import uikit.mvi.AsyncState
import uikit.mvi.UiState

data class JettonScreenState(
    val walletType: WalletType = WalletType.Default,
    val asyncState: AsyncState = AsyncState.Loading,
    val jetton: JettonBalance? = null,
    val currencyBalance: String = "",
    val rateFormat: String = "",
    val rate24h: String = "",
    val historyItems: List<HistoryItem> = emptyList(),
    val loadedAll: Boolean = false,
): UiState() {

    val balance: String
        get() {
            val jetton = jetton ?: return ""
            return CurrencyFormatter.format(jetton.jetton.symbol, jetton.parsedBalance, jetton.jetton.decimals)
        }

    fun getTopItems(): List<JettonItem> {
        val jetton = jetton ?: return emptyList()
        val items = mutableListOf<JettonItem>()
        items.add(JettonItem.Header(
            balance = balance,
            currencyBalance = currencyBalance,
            iconUrl = jetton.jetton.image,
            rate = rateFormat,
            diff24h = rate24h
        ))
        items.add(JettonItem.Divider)
        items.add(JettonItem.Actions(jetton, walletType))
        items.add(JettonItem.Divider)
        return items
    }
}