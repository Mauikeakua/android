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

package io.batteryapi.models


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param type 
 * @param rate 
 * @param symbol 
 * @param decimals 
 * @param supportGasless 
 * @param supportRecharge 
 * @param image 
 * @param jettonMaster 
 * @param minBootstrapValue 
 */
@Serializable

data class RechargeMethodsMethodsInner (

    @SerialName(value = "type")
    val type: RechargeMethodsMethodsInner.Type,

    @SerialName(value = "rate")
    val rate: kotlin.String,

    @SerialName(value = "symbol")
    val symbol: kotlin.String,

    @SerialName(value = "decimals")
    val decimals: kotlin.Int,

    @SerialName(value = "support_gasless")
    val supportGasless: kotlin.Boolean,

    @SerialName(value = "support_recharge")
    val supportRecharge: kotlin.Boolean,

    @SerialName(value = "image")
    val image: kotlin.String? = null,

    @SerialName(value = "jetton_master")
    val jettonMaster: kotlin.String? = null,

    @SerialName(value = "min_bootstrap_value")
    val minBootstrapValue: kotlin.String? = null

) {

    /**
     * 
     *
     * Values: jetton,ton
     */
    @Serializable
    enum class Type(val value: kotlin.String) {
        @SerialName(value = "jetton") jetton("jetton"),
        @SerialName(value = "ton") ton("ton");
    }
}

