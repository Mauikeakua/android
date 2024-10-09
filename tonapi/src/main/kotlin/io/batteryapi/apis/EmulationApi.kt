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

package io.batteryapi.apis

import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.HttpUrl

import io.batteryapi.models.EmulateMessageToWalletRequest
import io.batteryapi.models.GetTonConnectPayloadDefaultResponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import io.batteryapi.infrastructure.ApiClient
import io.batteryapi.infrastructure.ApiResponse
import io.batteryapi.infrastructure.ClientException
import io.batteryapi.infrastructure.ClientError
import io.batteryapi.infrastructure.ServerException
import io.batteryapi.infrastructure.ServerError
import io.batteryapi.infrastructure.MultiValueMap
import io.batteryapi.infrastructure.PartConfig
import io.batteryapi.infrastructure.RequestConfig
import io.batteryapi.infrastructure.RequestMethod
import io.batteryapi.infrastructure.ResponseType
import io.batteryapi.infrastructure.Success
import io.batteryapi.infrastructure.toMultiValue

class EmulationApi(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "https://battery.tonkeeper.com")
        }
    }

    /**
     * 
     * Emulate sending message to blockchain
     * @param xTonConnectAuth 
     * @param emulateMessageToWalletRequest bag-of-cells serialized to base64
     * @param acceptLanguage  (optional, default to "en")
     * @return kotlin.collections.Map<kotlin.String, kotlin.Any>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun emulateMessageToWallet(xTonConnectAuth: kotlin.String, emulateMessageToWalletRequest: EmulateMessageToWalletRequest, acceptLanguage: kotlin.String? = "en") : kotlin.collections.Map<kotlin.String, kotlin.Any> {
        val localVarResponse = emulateMessageToWalletWithHttpInfo(xTonConnectAuth = xTonConnectAuth, emulateMessageToWalletRequest = emulateMessageToWalletRequest, acceptLanguage = acceptLanguage)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.collections.Map<kotlin.String, kotlin.Any>
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * 
     * Emulate sending message to blockchain
     * @param xTonConnectAuth 
     * @param emulateMessageToWalletRequest bag-of-cells serialized to base64
     * @param acceptLanguage  (optional, default to "en")
     * @return ApiResponse<kotlin.collections.Map<kotlin.String, kotlin.Any>?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun emulateMessageToWalletWithHttpInfo(xTonConnectAuth: kotlin.String, emulateMessageToWalletRequest: EmulateMessageToWalletRequest, acceptLanguage: kotlin.String?) : ApiResponse<kotlin.collections.Map<kotlin.String, kotlin.Any>?> {
        val localVariableConfig = emulateMessageToWalletRequestConfig(xTonConnectAuth = xTonConnectAuth, emulateMessageToWalletRequest = emulateMessageToWalletRequest, acceptLanguage = acceptLanguage)

        return request<EmulateMessageToWalletRequest, kotlin.collections.Map<kotlin.String, kotlin.Any>>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation emulateMessageToWallet
     *
     * @param xTonConnectAuth 
     * @param emulateMessageToWalletRequest bag-of-cells serialized to base64
     * @param acceptLanguage  (optional, default to "en")
     * @return RequestConfig
     */
    fun emulateMessageToWalletRequestConfig(xTonConnectAuth: kotlin.String, emulateMessageToWalletRequest: EmulateMessageToWalletRequest, acceptLanguage: kotlin.String?) : RequestConfig<EmulateMessageToWalletRequest> {
        val localVariableBody = emulateMessageToWalletRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        acceptLanguage?.apply { localVariableHeaders["Accept-Language"] = this.toString() }
        xTonConnectAuth.apply { localVariableHeaders["X-TonConnect-Auth"] = this.toString() }
        localVariableHeaders["Content-Type"] = "application/json"
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/wallet/emulate",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * 
     * 
     * @param name 
     * @return kotlin.collections.Map<kotlin.String, kotlin.Any>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getJettonMetadata(name: kotlin.String) : kotlin.collections.Map<kotlin.String, kotlin.Any> {
        val localVarResponse = getJettonMetadataWithHttpInfo(name = name)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.collections.Map<kotlin.String, kotlin.Any>
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * 
     * 
     * @param name 
     * @return ApiResponse<kotlin.collections.Map<kotlin.String, kotlin.Any>?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getJettonMetadataWithHttpInfo(name: kotlin.String) : ApiResponse<kotlin.collections.Map<kotlin.String, kotlin.Any>?> {
        val localVariableConfig = getJettonMetadataRequestConfig(name = name)

        return request<Unit, kotlin.collections.Map<kotlin.String, kotlin.Any>>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getJettonMetadata
     *
     * @param name 
     * @return RequestConfig
     */
    fun getJettonMetadataRequestConfig(name: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/jetton-metadata/{name}.json".replace("{"+"name"+"}", encodeURIComponent(name.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}
