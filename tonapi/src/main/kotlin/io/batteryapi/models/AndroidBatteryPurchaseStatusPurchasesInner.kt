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

import io.batteryapi.models.AndroidBatteryPurchaseStatusPurchasesInnerError

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param productId 
 * @param token 
 * @param success 
 * @param error 
 */
@Serializable

data class AndroidBatteryPurchaseStatusPurchasesInner (

    @SerialName(value = "product_id")
    val productId: kotlin.String,

    @SerialName(value = "token")
    val token: kotlin.String,

    @SerialName(value = "success")
    val success: kotlin.Boolean,

    @SerialName(value = "error")
    val error: AndroidBatteryPurchaseStatusPurchasesInnerError? = null

)

