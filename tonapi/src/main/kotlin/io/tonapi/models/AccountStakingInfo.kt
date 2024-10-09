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
 * @param pool 
 * @param amount 
 * @param pendingDeposit 
 * @param pendingWithdraw 
 * @param readyWithdraw 
 */
@Serializable

data class AccountStakingInfo (

    @SerialName(value = "pool")
    val pool: kotlin.String,

    @SerialName(value = "amount")
    val amount: kotlin.Long,

    @SerialName(value = "pending_deposit")
    val pendingDeposit: kotlin.Long,

    @SerialName(value = "pending_withdraw")
    val pendingWithdraw: kotlin.Long,

    @SerialName(value = "ready_withdraw")
    val readyWithdraw: kotlin.Long

)

