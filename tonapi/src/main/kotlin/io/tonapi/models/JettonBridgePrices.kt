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


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param bridgeBurnFee 
 * @param bridgeMintFee 
 * @param walletMinTonsForStorage 
 * @param walletGasConsumption 
 * @param minterMinTonsForStorage 
 * @param discoverGasConsumption 
 */
@Serializable

data class JettonBridgePrices (

    @SerialName(value = "bridge_burn_fee")
    val bridgeBurnFee: kotlin.Long,

    @SerialName(value = "bridge_mint_fee")
    val bridgeMintFee: kotlin.Long,

    @SerialName(value = "wallet_min_tons_for_storage")
    val walletMinTonsForStorage: kotlin.Long,

    @SerialName(value = "wallet_gas_consumption")
    val walletGasConsumption: kotlin.Long,

    @SerialName(value = "minter_min_tons_for_storage")
    val minterMinTonsForStorage: kotlin.Long,

    @SerialName(value = "discover_gas_consumption")
    val discoverGasConsumption: kotlin.Long

)

