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

import io.tonapi.models.MultisigOrder

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param address 
 * @param seqno 
 * @param threshold 
 * @param signers 
 * @param proposers 
 * @param orders 
 */
@Serializable

data class Multisig (

    @SerialName(value = "address")
    val address: kotlin.String,

    @SerialName(value = "seqno")
    val seqno: kotlin.Long,

    @SerialName(value = "threshold")
    val threshold: kotlin.Int,

    @SerialName(value = "signers")
    val signers: kotlin.collections.List<kotlin.String>,

    @SerialName(value = "proposers")
    val proposers: kotlin.collections.List<kotlin.String>,

    @SerialName(value = "orders")
    val orders: kotlin.collections.List<MultisigOrder>

)

