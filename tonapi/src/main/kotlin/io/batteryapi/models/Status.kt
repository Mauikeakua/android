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

import io.batteryapi.models.StatusPendingTransactionsInner

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param pendingTransactions 
 */
@Serializable

data class Status (

    @SerialName(value = "pending_transactions")
    val pendingTransactions: kotlin.collections.List<StatusPendingTransactionsInner>

)

