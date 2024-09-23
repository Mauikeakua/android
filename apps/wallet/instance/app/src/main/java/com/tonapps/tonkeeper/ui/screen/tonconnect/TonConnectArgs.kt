package com.tonapps.tonkeeper.ui.screen.tonconnect

import android.net.Uri
import android.os.Bundle
import com.tonapps.extensions.getParcelableCompat
import com.tonapps.wallet.data.dapps.entities.AppEntity
import uikit.base.BaseArgs

data class TonConnectArgs(
    val app: AppEntity,
    val proofPayload: String?,
    val returnUri: Uri?
): BaseArgs() {

    private companion object {
        private const val ARG_APP = "app"
        private const val ARG_PROOF_PAYLOAD = "proofPayload"
        private const val ARG_RETURN_URI = "returnUri"
    }

    constructor(bundle: Bundle) : this(
        app = bundle.getParcelableCompat(ARG_APP)!!,
        proofPayload = bundle.getString(ARG_PROOF_PAYLOAD),
        returnUri = bundle.getParcelableCompat(ARG_RETURN_URI)
    )

    override fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putParcelable(ARG_APP, app)
        bundle.putString(ARG_PROOF_PAYLOAD, proofPayload)
        bundle.putParcelable(ARG_RETURN_URI, returnUri)
        return bundle
    }
}