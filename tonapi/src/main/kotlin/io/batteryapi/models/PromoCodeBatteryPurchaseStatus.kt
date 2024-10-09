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

import io.batteryapi.models.PromoCodeBatteryPurchaseStatusError

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param balanceChange 
 * @param success 
 * @param error 
 */
@Serializable

data class PromoCodeBatteryPurchaseStatus (

    @SerialName(value = "balance_change")
    val balanceChange: kotlin.String,

    @SerialName(value = "success")
    val success: kotlin.Boolean,

    @SerialName(value = "error")
    val error: PromoCodeBatteryPurchaseStatusError? = null

)

