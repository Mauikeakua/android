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

import io.tonapi.models.JettonQuantity
import io.tonapi.models.NftItem

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * Risk specifies assets that could be lost if a message would be sent to a malicious smart contract. It makes sense to understand the risk BEFORE sending a message to the blockchain.
 *
 * @param transferAllRemainingBalance transfer all the remaining balance of the wallet.
 * @param ton 
 * @param jettons 
 * @param nfts 
 */
@Serializable

data class Risk (

    /* transfer all the remaining balance of the wallet. */
    @SerialName(value = "transfer_all_remaining_balance")
    val transferAllRemainingBalance: kotlin.Boolean,

    @SerialName(value = "ton")
    val ton: kotlin.Long,

    @SerialName(value = "jettons")
    val jettons: kotlin.collections.List<JettonQuantity>,

    @SerialName(value = "nfts")
    val nfts: kotlin.collections.List<NftItem>

)

