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
import io.tonapi.models.JettonMetadata
import io.tonapi.models.JettonVerificationType

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param mintable 
 * @param totalSupply 
 * @param metadata 
 * @param verification 
 * @param holdersCount 
 * @param admin 
 */
@Serializable

data class JettonInfo (

    @SerialName(value = "mintable")
    val mintable: kotlin.Boolean,

    @SerialName(value = "total_supply")
    val totalSupply: kotlin.String,

    @SerialName(value = "metadata")
    val metadata: JettonMetadata,

    @Contextual @SerialName(value = "verification")
    val verification: JettonVerificationType,

    @SerialName(value = "holders_count")
    val holdersCount: kotlin.Int,

    @SerialName(value = "admin")
    val admin: AccountAddress? = null

)

