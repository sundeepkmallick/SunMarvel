package com.sunmarvel.network.ktor

import android.util.Log
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class NetworkResult<out T> {
    data class Success<T>(val result: T) : NetworkResult<T>()
    data class Error<Nothing>(val body: ErrorResponse, val exception: Exception) :
        NetworkResult<Nothing>()
}

class RequestHandler(val httpClient: HttpClient) {

    suspend inline fun <reified B, reified R> executeRequest(
        headers: List<Pair<String, String>>? = null,
        method: HttpMethod,
        urlPath: String,
        body: B? = null,
        queryParams: Map<String, Any>? = null
    ): NetworkResult<R> {
        return withContext(Dispatchers.IO) {
            try {
                val httpResponse = httpClient.prepareRequest {
                    this.method = method
                    headers?.forEach { (key, value) ->
                        header(key, value)
                    }
                    url {
                        appendPathSegments(urlPath)
                    }
                    body?.let { setBody(it) }
                    queryParams?.let { params ->
                        params.forEach { (key, value) ->
                            parameter(key, value)
                        }
                    }
                }.execute()
                Log.d("executeRequest", "executeRequest: $httpResponse")
                Log.d("executeRequest", "executeRequest body: ${httpResponse.bodyAsText()}")
                val response = httpResponse.body<R>()
                NetworkResult.Success(response)
            } catch (exception: Exception) {
                val exceptionMessage = exception.message ?: "Unknown Error"
                var errorResponse = ErrorResponse(exceptionMessage, "", null, 0)

                val networkException = if (exception is ResponseException) {
                    errorResponse = try {
                        Gson().fromJson(exception.response.bodyAsText(), ErrorResponse::class.java)
                    } catch (e: Exception) {
                        ErrorResponse(exceptionMessage, "", null, exception.response.status.value)
                    }

                    when (exception.response.status) {
                        HttpStatusCode.Unauthorized -> NetworkException.UnAuthorizedException(
                            errorResponse.message,
                            exception
                        )

                        HttpStatusCode.Forbidden -> NetworkException.ForbiddenException(
                            errorResponse.message,
                            exception
                        )

                        HttpStatusCode.NotFound -> NetworkException.NotFoundException(
                            errorResponse.message,
                            exception
                        )

                        HttpStatusCode.BadRequest -> NetworkException.BadRequestException(
                            errorResponse.message,
                            exception
                        )

                        HttpStatusCode.InternalServerError -> NetworkException.InternalServerException(
                            errorResponse.message,
                            exception
                        )

                        HttpStatusCode.ServiceUnavailable -> NetworkException.UnknownNetworkErrorException(
                            errorResponse.message,
                            exception
                        )

                        HttpStatusCode.GatewayTimeout -> NetworkException.SocketTimeoutException(
                            errorResponse.message,
                            exception
                        )

                        HttpStatusCode.UnprocessableEntity -> NetworkException.BadRequestException(
                            errorResponse.message,
                            exception
                        )

                        else -> NetworkException.NotFoundException(errorResponse.message, exception)
                    }
                } else {
                    NetworkException.UnknownException(exceptionMessage, exception)
                }
                NetworkResult.Error(errorResponse, networkException)
            }
        }
    }

    suspend inline fun <reified R> get(
        headers: List<Pair<String, String>>? = null,
        urlPath: String,
        queryParams: Map<String, Any>? = null
    ): NetworkResult<R> =
        executeRequest<Any, R>(
            headers = headers,
            method = HttpMethod.Get,
            urlPath = urlPath,
            queryParams = queryParams
        )

    suspend inline fun <reified B, reified R> post(
        urlPath: String,
        body: B? = null
    ): NetworkResult<R> = executeRequest(
        method = HttpMethod.Post,
        urlPath = urlPath,
        body = body
    )

    suspend inline fun <reified B, reified R> delete(
        urlPath: String,
        body: B? = null
    ): NetworkResult<R> = executeRequest(
        method = HttpMethod.Delete,
        urlPath = urlPath,
        body = body
    )
}