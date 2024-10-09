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
 * The configuration for the consensus protocol above catchain.
 *
 * @param roundCandidates 
 * @param nextCandidateDelayMs 
 * @param consensusTimeoutMs 
 * @param fastAttempts 
 * @param attemptDuration 
 * @param catchainMaxDeps 
 * @param maxBlockBytes 
 * @param maxCollatedBytes 
 * @param flags 
 * @param newCatchainIds 
 * @param protoVersion 
 * @param catchainMaxBlocksCoeff 
 */
@Serializable

data class BlockchainConfig29 (

    @SerialName(value = "round_candidates")
    val roundCandidates: kotlin.Long,

    @SerialName(value = "next_candidate_delay_ms")
    val nextCandidateDelayMs: kotlin.Long,

    @SerialName(value = "consensus_timeout_ms")
    val consensusTimeoutMs: kotlin.Long,

    @SerialName(value = "fast_attempts")
    val fastAttempts: kotlin.Long,

    @SerialName(value = "attempt_duration")
    val attemptDuration: kotlin.Long,

    @SerialName(value = "catchain_max_deps")
    val catchainMaxDeps: kotlin.Long,

    @SerialName(value = "max_block_bytes")
    val maxBlockBytes: kotlin.Long,

    @SerialName(value = "max_collated_bytes")
    val maxCollatedBytes: kotlin.Long,

    @SerialName(value = "flags")
    val flags: kotlin.Int? = null,

    @SerialName(value = "new_catchain_ids")
    val newCatchainIds: kotlin.Boolean? = null,

    @SerialName(value = "proto_version")
    val protoVersion: kotlin.Long? = null,

    @SerialName(value = "catchain_max_blocks_coeff")
    val catchainMaxBlocksCoeff: kotlin.Long? = null

)

