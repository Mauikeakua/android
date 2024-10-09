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

import io.tonapi.models.OracleBridgeParams

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * Bridge parameters for wrapping TON in other networks.
 *
 * @param oracleBridgeParams 
 */
@Serializable

data class BlockchainConfig71 (

    @SerialName(value = "oracle_bridge_params")
    val oracleBridgeParams: OracleBridgeParams

)

