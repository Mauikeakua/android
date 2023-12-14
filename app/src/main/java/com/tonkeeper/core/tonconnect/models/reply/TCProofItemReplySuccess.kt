package com.tonkeeper.core.tonconnect.models.reply

import org.json.JSONObject

data class TCProofItemReplySuccess(
    val name: String = "ton_proof",
    val proof: Proof
): TCReply() {

    data class Domain(
        val lengthBytes: Long,
        val value: String
    ): TCBase() {
        override fun toJSON(): JSONObject {
            val json = JSONObject()
            json.put("lengthBytes", lengthBytes)
            json.put("value", value)
            return json
        }
    }

    data class Proof(
        val timestamp: Int,
        val domain: Domain,
        val payload: String,
        val signature: String
    ): TCBase() {
        override fun toJSON(): JSONObject {
            val json = JSONObject()
            json.put("timestamp", timestamp)
            json.put("domain", domain.toJSON())
            json.put("payload", payload)
            json.put("signature", signature)
            return json
        }
    }

    override fun toJSON(): JSONObject {
        val json = JSONObject()
        json.put("name", name)
        json.put("proof", proof.toJSON())
        return json
    }
}
