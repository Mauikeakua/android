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

import io.batteryapi.models.IosBatteryPurchaseRequestTransactionsInner

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param transactions 
 */
@Serializable

data class IosBatteryPurchaseRequest (

    @SerialName(value = "transactions")
    val transactions: kotlin.collections.List<IosBatteryPurchaseRequestTransactionsInner>

)

