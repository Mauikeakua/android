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
 * @param id 
 * @param `value` 
 */
@Serializable

data class BlockCurrencyCollectionOtherInner (

    @SerialName(value = "id")
    val id: kotlin.Long,

    @SerialName(value = "value")
    val `value`: kotlin.String

)

