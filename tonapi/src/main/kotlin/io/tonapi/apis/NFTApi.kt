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

package io.tonapi.apis

import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.HttpUrl

import io.tonapi.models.AccountEvents
import io.tonapi.models.GetAccountsRequest
import io.tonapi.models.NftCollection
import io.tonapi.models.NftCollections
import io.tonapi.models.NftItem
import io.tonapi.models.NftItems
import io.tonapi.models.StatusDefaultResponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import io.tonapi.infrastructure.ApiClient
import io.tonapi.infrastructure.ApiResponse
import io.tonapi.infrastructure.ClientException
import io.tonapi.infrastructure.ClientError
import io.tonapi.infrastructure.ServerException
import io.tonapi.infrastructure.ServerError
import io.tonapi.infrastructure.MultiValueMap
import io.tonapi.infrastructure.PartConfig
import io.tonapi.infrastructure.RequestConfig
import io.tonapi.infrastructure.RequestMethod
import io.tonapi.infrastructure.ResponseType
import io.tonapi.infrastructure.Success
import io.tonapi.infrastructure.toMultiValue

class NFTApi(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "https://tonapi.io")
        }
    }

    /**
     * 
     * Get the transfer nft history
     * @param accountId account ID
     * @param limit 
     * @param acceptLanguage  (optional, default to "en")
     * @param beforeLt omit this parameter to get last events (optional)
     * @param startDate  (optional)
     * @param endDate  (optional)
     * @return AccountEvents
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getAccountNftHistory(accountId: kotlin.String, limit: kotlin.Int, acceptLanguage: kotlin.String? = "en", beforeLt: kotlin.Long? = null, startDate: kotlin.Long? = null, endDate: kotlin.Long? = null) : AccountEvents {
        val localVarResponse = getAccountNftHistoryWithHttpInfo(accountId = accountId, limit = limit, acceptLanguage = acceptLanguage, beforeLt = beforeLt, startDate = startDate, endDate = endDate)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as AccountEvents
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
     * Get the transfer nft history
     * @param accountId account ID
     * @param limit 
     * @param acceptLanguage  (optional, default to "en")
     * @param beforeLt omit this parameter to get last events (optional)
     * @param startDate  (optional)
     * @param endDate  (optional)
     * @return ApiResponse<AccountEvents?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getAccountNftHistoryWithHttpInfo(accountId: kotlin.String, limit: kotlin.Int, acceptLanguage: kotlin.String?, beforeLt: kotlin.Long?, startDate: kotlin.Long?, endDate: kotlin.Long?) : ApiResponse<AccountEvents?> {
        val localVariableConfig = getAccountNftHistoryRequestConfig(accountId = accountId, limit = limit, acceptLanguage = acceptLanguage, beforeLt = beforeLt, startDate = startDate, endDate = endDate)

        return request<Unit, AccountEvents>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getAccountNftHistory
     *
     * @param accountId account ID
     * @param limit 
     * @param acceptLanguage  (optional, default to "en")
     * @param beforeLt omit this parameter to get last events (optional)
     * @param startDate  (optional)
     * @param endDate  (optional)
     * @return RequestConfig
     */
    fun getAccountNftHistoryRequestConfig(accountId: kotlin.String, limit: kotlin.Int, acceptLanguage: kotlin.String?, beforeLt: kotlin.Long?, startDate: kotlin.Long?, endDate: kotlin.Long?) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, kotlin.collections.List<kotlin.String>>()
            .apply {
                if (beforeLt != null) {
                    put("before_lt", listOf(beforeLt.toString()))
                }
                put("limit", listOf(limit.toString()))
                if (startDate != null) {
                    put("start_date", listOf(startDate.toString()))
                }
                if (endDate != null) {
                    put("end_date", listOf(endDate.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        acceptLanguage?.apply { localVariableHeaders["Accept-Language"] = this.toString() }
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v2/accounts/{account_id}/nfts/history".replace("{"+"account_id"+"}", encodeURIComponent(accountId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * 
     * Get NFT items from collection by collection address
     * @param accountId account ID
     * @param limit  (optional, default to 1000)
     * @param offset  (optional, default to 0)
     * @return NftItems
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getItemsFromCollection(accountId: kotlin.String, limit: kotlin.Int? = 1000, offset: kotlin.Int? = 0) : NftItems {
        val localVarResponse = getItemsFromCollectionWithHttpInfo(accountId = accountId, limit = limit, offset = offset)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as NftItems
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
     * Get NFT items from collection by collection address
     * @param accountId account ID
     * @param limit  (optional, default to 1000)
     * @param offset  (optional, default to 0)
     * @return ApiResponse<NftItems?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getItemsFromCollectionWithHttpInfo(accountId: kotlin.String, limit: kotlin.Int?, offset: kotlin.Int?) : ApiResponse<NftItems?> {
        val localVariableConfig = getItemsFromCollectionRequestConfig(accountId = accountId, limit = limit, offset = offset)

        return request<Unit, NftItems>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getItemsFromCollection
     *
     * @param accountId account ID
     * @param limit  (optional, default to 1000)
     * @param offset  (optional, default to 0)
     * @return RequestConfig
     */
    fun getItemsFromCollectionRequestConfig(accountId: kotlin.String, limit: kotlin.Int?, offset: kotlin.Int?) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, kotlin.collections.List<kotlin.String>>()
            .apply {
                if (limit != null) {
                    put("limit", listOf(limit.toString()))
                }
                if (offset != null) {
                    put("offset", listOf(offset.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v2/nfts/collections/{account_id}/items".replace("{"+"account_id"+"}", encodeURIComponent(accountId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * 
     * Get NFT collection by collection address
     * @param accountId account ID
     * @return NftCollection
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getNftCollection(accountId: kotlin.String) : NftCollection {
        val localVarResponse = getNftCollectionWithHttpInfo(accountId = accountId)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as NftCollection
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
     * Get NFT collection by collection address
     * @param accountId account ID
     * @return ApiResponse<NftCollection?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getNftCollectionWithHttpInfo(accountId: kotlin.String) : ApiResponse<NftCollection?> {
        val localVariableConfig = getNftCollectionRequestConfig(accountId = accountId)

        return request<Unit, NftCollection>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getNftCollection
     *
     * @param accountId account ID
     * @return RequestConfig
     */
    fun getNftCollectionRequestConfig(accountId: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v2/nfts/collections/{account_id}".replace("{"+"account_id"+"}", encodeURIComponent(accountId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * 
     * Get NFT collection items by their addresses
     * @param getAccountsRequest a list of account ids (optional)
     * @return NftCollections
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getNftCollectionItemsByAddresses(getAccountsRequest: GetAccountsRequest? = null) : NftCollections {
        val localVarResponse = getNftCollectionItemsByAddressesWithHttpInfo(getAccountsRequest = getAccountsRequest)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as NftCollections
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
     * Get NFT collection items by their addresses
     * @param getAccountsRequest a list of account ids (optional)
     * @return ApiResponse<NftCollections?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getNftCollectionItemsByAddressesWithHttpInfo(getAccountsRequest: GetAccountsRequest?) : ApiResponse<NftCollections?> {
        val localVariableConfig = getNftCollectionItemsByAddressesRequestConfig(getAccountsRequest = getAccountsRequest)

        return request<GetAccountsRequest, NftCollections>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getNftCollectionItemsByAddresses
     *
     * @param getAccountsRequest a list of account ids (optional)
     * @return RequestConfig
     */
    fun getNftCollectionItemsByAddressesRequestConfig(getAccountsRequest: GetAccountsRequest?) : RequestConfig<GetAccountsRequest> {
        val localVariableBody = getAccountsRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v2/nfts/collections/_bulk",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * 
     * Get NFT collections
     * @param limit  (optional, default to 100)
     * @param offset  (optional, default to 0)
     * @return NftCollections
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getNftCollections(limit: kotlin.Int? = 100, offset: kotlin.Int? = 0) : NftCollections {
        val localVarResponse = getNftCollectionsWithHttpInfo(limit = limit, offset = offset)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as NftCollections
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
     * Get NFT collections
     * @param limit  (optional, default to 100)
     * @param offset  (optional, default to 0)
     * @return ApiResponse<NftCollections?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getNftCollectionsWithHttpInfo(limit: kotlin.Int?, offset: kotlin.Int?) : ApiResponse<NftCollections?> {
        val localVariableConfig = getNftCollectionsRequestConfig(limit = limit, offset = offset)

        return request<Unit, NftCollections>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getNftCollections
     *
     * @param limit  (optional, default to 100)
     * @param offset  (optional, default to 0)
     * @return RequestConfig
     */
    fun getNftCollectionsRequestConfig(limit: kotlin.Int?, offset: kotlin.Int?) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, kotlin.collections.List<kotlin.String>>()
            .apply {
                if (limit != null) {
                    put("limit", listOf(limit.toString()))
                }
                if (offset != null) {
                    put("offset", listOf(offset.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v2/nfts/collections",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * 
     * Get the transfer nfts history for account
     * @param accountId account ID
     * @param limit 
     * @param acceptLanguage  (optional, default to "en")
     * @param beforeLt omit this parameter to get last events (optional)
     * @param startDate  (optional)
     * @param endDate  (optional)
     * @return AccountEvents
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getNftHistoryByID(accountId: kotlin.String, limit: kotlin.Int, acceptLanguage: kotlin.String? = "en", beforeLt: kotlin.Long? = null, startDate: kotlin.Long? = null, endDate: kotlin.Long? = null) : AccountEvents {
        val localVarResponse = getNftHistoryByIDWithHttpInfo(accountId = accountId, limit = limit, acceptLanguage = acceptLanguage, beforeLt = beforeLt, startDate = startDate, endDate = endDate)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as AccountEvents
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
     * Get the transfer nfts history for account
     * @param accountId account ID
     * @param limit 
     * @param acceptLanguage  (optional, default to "en")
     * @param beforeLt omit this parameter to get last events (optional)
     * @param startDate  (optional)
     * @param endDate  (optional)
     * @return ApiResponse<AccountEvents?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getNftHistoryByIDWithHttpInfo(accountId: kotlin.String, limit: kotlin.Int, acceptLanguage: kotlin.String?, beforeLt: kotlin.Long?, startDate: kotlin.Long?, endDate: kotlin.Long?) : ApiResponse<AccountEvents?> {
        val localVariableConfig = getNftHistoryByIDRequestConfig(accountId = accountId, limit = limit, acceptLanguage = acceptLanguage, beforeLt = beforeLt, startDate = startDate, endDate = endDate)

        return request<Unit, AccountEvents>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getNftHistoryByID
     *
     * @param accountId account ID
     * @param limit 
     * @param acceptLanguage  (optional, default to "en")
     * @param beforeLt omit this parameter to get last events (optional)
     * @param startDate  (optional)
     * @param endDate  (optional)
     * @return RequestConfig
     */
    fun getNftHistoryByIDRequestConfig(accountId: kotlin.String, limit: kotlin.Int, acceptLanguage: kotlin.String?, beforeLt: kotlin.Long?, startDate: kotlin.Long?, endDate: kotlin.Long?) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, kotlin.collections.List<kotlin.String>>()
            .apply {
                if (beforeLt != null) {
                    put("before_lt", listOf(beforeLt.toString()))
                }
                put("limit", listOf(limit.toString()))
                if (startDate != null) {
                    put("start_date", listOf(startDate.toString()))
                }
                if (endDate != null) {
                    put("end_date", listOf(endDate.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        acceptLanguage?.apply { localVariableHeaders["Accept-Language"] = this.toString() }
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v2/nfts/{account_id}/history".replace("{"+"account_id"+"}", encodeURIComponent(accountId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * 
     * Get NFT item by its address
     * @param accountId account ID
     * @return NftItem
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getNftItemByAddress(accountId: kotlin.String) : NftItem {
        val localVarResponse = getNftItemByAddressWithHttpInfo(accountId = accountId)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as NftItem
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
     * Get NFT item by its address
     * @param accountId account ID
     * @return ApiResponse<NftItem?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getNftItemByAddressWithHttpInfo(accountId: kotlin.String) : ApiResponse<NftItem?> {
        val localVariableConfig = getNftItemByAddressRequestConfig(accountId = accountId)

        return request<Unit, NftItem>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getNftItemByAddress
     *
     * @param accountId account ID
     * @return RequestConfig
     */
    fun getNftItemByAddressRequestConfig(accountId: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/v2/nfts/{account_id}".replace("{"+"account_id"+"}", encodeURIComponent(accountId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * 
     * Get NFT items by their addresses
     * @param getAccountsRequest a list of account ids (optional)
     * @return NftItems
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getNftItemsByAddresses(getAccountsRequest: GetAccountsRequest? = null) : NftItems {
        val localVarResponse = getNftItemsByAddressesWithHttpInfo(getAccountsRequest = getAccountsRequest)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as NftItems
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
     * Get NFT items by their addresses
     * @param getAccountsRequest a list of account ids (optional)
     * @return ApiResponse<NftItems?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getNftItemsByAddressesWithHttpInfo(getAccountsRequest: GetAccountsRequest?) : ApiResponse<NftItems?> {
        val localVariableConfig = getNftItemsByAddressesRequestConfig(getAccountsRequest = getAccountsRequest)

        return request<GetAccountsRequest, NftItems>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getNftItemsByAddresses
     *
     * @param getAccountsRequest a list of account ids (optional)
     * @return RequestConfig
     */
    fun getNftItemsByAddressesRequestConfig(getAccountsRequest: GetAccountsRequest?) : RequestConfig<GetAccountsRequest> {
        val localVariableBody = getAccountsRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/v2/nfts/_bulk",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}
