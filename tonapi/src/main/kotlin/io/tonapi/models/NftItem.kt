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
import io.tonapi.models.ImagePreview
import io.tonapi.models.NftItemCollection
import io.tonapi.models.Sale
import io.tonapi.models.TrustType

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * 
 *
 * @param address 
 * @param index 
 * @param verified 
 * @param metadata 
 * @param approvedBy 
 * @param trust 
 * @param owner 
 * @param collection 
 * @param sale 
 * @param previews 
 * @param dns 
 * @param includeCnft 
 */
@Serializable

data class NftItem (

    @SerialName(value = "address")
    val address: kotlin.String,

    @SerialName(value = "index")
    val index: kotlin.Long,

    @SerialName(value = "verified")
    val verified: kotlin.Boolean,

    @Contextual @SerialName(value = "metadata")
    val metadata: kotlin.collections.Map<kotlin.String, kotlin.String>,

    @SerialName(value = "approved_by")
    val approvedBy: kotlin.collections.List<NftItem.ApprovedBy>,

    @Contextual @SerialName(value = "trust")
    val trust: TrustType,

    @SerialName(value = "owner")
    val owner: AccountAddress? = null,

    @SerialName(value = "collection")
    val collection: NftItemCollection? = null,

    @SerialName(value = "sale")
    val sale: Sale? = null,

    @SerialName(value = "previews")
    val previews: kotlin.collections.List<ImagePreview>? = null,

    @SerialName(value = "dns")
    val dns: kotlin.String? = null,

    @SerialName(value = "include_cnft")
    val includeCnft: kotlin.Boolean? = null

) {

    /**
     * 
     *
     * Values: getgems,tonkeeper,tonPeriodDiamonds
     */
    @Serializable
    enum class ApprovedBy(val value: kotlin.String) {
        @SerialName(value = "getgems") getgems("getgems"),
        @SerialName(value = "tonkeeper") tonkeeper("tonkeeper"),
        @SerialName(value = "ton.diamonds") tonPeriodDiamonds("ton.diamonds");
    }
}

