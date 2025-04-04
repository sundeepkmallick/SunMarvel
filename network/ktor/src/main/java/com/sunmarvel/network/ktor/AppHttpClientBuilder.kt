package com.sunmarvel.network.ktor

import com.sunmarvel.storage.StorageHandler
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject


class AppHttpClientBuilder @Inject constructor() {
    private lateinit var protocol: URLProtocol
    private lateinit var host: String
    private var port: Int? = null
    private var headers: List<Pair<String, String>>? = null
    private var authType: AuthType? = AuthType.NONE
    private var queryParameters: MutableList<Pair<String, String>> = mutableListOf()

    companion object {
        private const val KEEP_ALIVE_TIME: Long = 60000
        private const val CONNECT_TIMEOUT: Long = 60000
        private const val READ_TIMEOUT: Long = 60000
        private const val CONNECT_ATTEMPTS: Int = 3
    }

    fun protocol(protocol: URLProtocol) = apply { this.protocol = protocol }
    fun host(host: String) = apply { this.host = host }
    fun port(port: Int) = apply { this.port = port }
    fun authType(authType: AuthType) = apply{ this.authType = authType}
    fun headers(headers: List<Pair<String, String>>) = apply { this.headers = headers }
    fun queryParameters(params: List<Pair<String, String>>) = apply { this.queryParameters.addAll(params) }

    fun build(): HttpClient {
        return HttpClient(CIO) {
            expectSuccess = true

            engine {
                endpoint {
                    keepAliveTime = KEEP_ALIVE_TIME
                    connectTimeout = CONNECT_TIMEOUT
                    connectAttempts = CONNECT_ATTEMPTS
                }
            }

            defaultRequest {
                url {
                    protocol = this@AppHttpClientBuilder.protocol
                    host = this@AppHttpClientBuilder.host
                    this@AppHttpClientBuilder.port?.let { port = it }
                    parameters.clear()
                    this@AppHttpClientBuilder.queryParameters.forEach { (key, value) ->
                        parameters.append(key, value)
                    }
                }

                /*parameters {
                    this@AppHttpClientBuilder.queryParameters.forEach { (key, value) ->
                        append(key, value)
                    }
                }*/

                headers {
                    this@AppHttpClientBuilder.headers?.forEach { (key, value) ->
                        append(key, value)
                    }

                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        explicitNulls = false
                        /*prettyPrint = true
                        isLenient = true*/
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Auth) {

            }
            install(Logging) {
                logger = object : io.ktor.client.plugins.logging.Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
}