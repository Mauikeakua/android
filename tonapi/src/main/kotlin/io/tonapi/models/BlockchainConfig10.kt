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
 * List of critical TON parameters, the change of which significantly affects the network, so more voting rounds are held.
 *
 * @param criticalParams 
 */
@Serializable

data class BlockchainConfig10 (

    @SerialName(value = "critical_params")
    val criticalParams: kotlin.collections.List<kotlin.Int>

)

