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

import io.tonapi.models.BlockRaw

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param id 
 * @param proof 
 * @param `data` 
 */
@Serializable

data class GetAllRawShardsInfo200Response (

    @SerialName(value = "id")
    val id: BlockRaw,

    @SerialName(value = "proof")
    val proof: kotlin.String,

    @SerialName(value = "data")
    val `data`: kotlin.String

)

