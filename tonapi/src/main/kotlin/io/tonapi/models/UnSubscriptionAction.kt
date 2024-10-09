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

import io.tonapi.models.AccountAddress

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param subscriber 
 * @param subscription 
 * @param beneficiary 
 */
@Serializable

data class UnSubscriptionAction (

    @SerialName(value = "subscriber")
    val subscriber: AccountAddress,

    @SerialName(value = "subscription")
    val subscription: kotlin.String,

    @SerialName(value = "beneficiary")
    val beneficiary: AccountAddress

)

