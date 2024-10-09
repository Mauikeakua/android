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
import io.tonapi.models.InitStateRaw

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param mode 
 * @param version 
 * @param capabilities 
 * @param last 
 * @param lastUtime 
 * @param now 
 * @param stateRootHash 
 * @param `init` 
 */
@Serializable

data class GetRawMasterchainInfoExt200Response (

    @SerialName(value = "mode")
    val mode: kotlin.Int,

    @SerialName(value = "version")
    val version: kotlin.Int,

    @SerialName(value = "capabilities")
    val capabilities: kotlin.Long,

    @SerialName(value = "last")
    val last: BlockRaw,

    @SerialName(value = "last_utime")
    val lastUtime: kotlin.Int,

    @SerialName(value = "now")
    val now: kotlin.Int,

    @SerialName(value = "state_root_hash")
    val stateRootHash: kotlin.String,

    @SerialName(value = "init")
    val `init`: InitStateRaw

)

