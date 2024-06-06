package com.tonapps.tonkeeper.ui.screen.send

import android.os.Bundle
import com.tonapps.wallet.api.entity.TokenEntity
import uikit.base.BaseArgs

data class SendArgs(
    val targetAddress: String?,
    val tokenAddress: String,
    val amountNano: Long,
    val text: String?,
): BaseArgs() {

    private companion object {
        private const val ARG_TARGET_ADDRESS = "target_address"
        private const val ARG_TOKEN_ADDRESS = "token_address"
        private const val ARG_AMOUNT_NANO = "amount_nano"
        private const val ARG_TEXT = "text"
    }

    constructor(bundle: Bundle) : this(
        targetAddress = bundle.getString(ARG_TARGET_ADDRESS),
        tokenAddress = bundle.getString(ARG_TOKEN_ADDRESS) ?: TokenEntity.TON.address,
        amountNano = bundle.getLong(ARG_AMOUNT_NANO),
        text = bundle.getString(ARG_TEXT)
    )

    override fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(ARG_TARGET_ADDRESS, targetAddress)
        bundle.putString(ARG_TOKEN_ADDRESS, tokenAddress)
        bundle.putLong(ARG_AMOUNT_NANO, amountNano)
        bundle.putString(ARG_TEXT, text)
        return bundle
    }
}