/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package io.tonapi.models

import io.tonapi.models.AccountAddress
import io.tonapi.models.Action

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * An event is built on top of a trace which is a series of transactions caused by one inbound message. TonAPI looks for known patterns inside the trace and splits the trace into actions, where a single action represents a meaningful high-level operation like a Jetton Transfer or an NFT Purchase. Actions are expected to be shown to users. It is advised not to build any logic on top of actions because actions can be changed at any time.
 *
 * @param eventId 
 * @param account 
 * @param timestamp 
 * @param actions 
 * @param isScam scam
 * @param lt 
 * @param inProgress Event is not finished yet. Transactions still happening
 * @param extra TODO
 */
@Serializable

data class AccountEvent (

    @SerialName(value = "event_id")
    val eventId: kotlin.String,

    @SerialName(value = "account")
    val account: AccountAddress,

    @SerialName(value = "timestamp")
    val timestamp: kotlin.Long,

    @SerialName(value = "actions")
    val actions: kotlin.collections.List<Action>,

    /* scam */
    @SerialName(value = "is_scam")
    val isScam: kotlin.Boolean,

    @SerialName(value = "lt")
    val lt: kotlin.Long,

    /* Event is not finished yet. Transactions still happening */
    @SerialName(value = "in_progress")
    val inProgress: kotlin.Boolean,

    /* TODO */
    @SerialName(value = "extra")
    val extra: kotlin.Long

)

