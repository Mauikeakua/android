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

import io.tonapi.models.GaslessConfigGasJettonsInner

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param relayAddress sending excess to this address decreases the commission of a gasless transfer
 * @param gasJettons list of jettons, any of them can be used to pay for gas
 */
@Serializable

data class GaslessConfig (

    /* sending excess to this address decreases the commission of a gasless transfer */
    @SerialName(value = "relay_address")
    val relayAddress: kotlin.String,

    /* list of jettons, any of them can be used to pay for gas */
    @SerialName(value = "gas_jettons")
    val gasJettons: kotlin.collections.List<GaslessConfigGasJettonsInner>

)

