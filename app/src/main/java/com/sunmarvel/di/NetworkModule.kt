package com.sunmarvel.di

import com.sunmarvel.BuildConfig
import com.sunmarvel.di.MarvelApiParams.Companion.APIKEY
import com.sunmarvel.di.MarvelApiParams.Companion.HASH
import com.sunmarvel.di.MarvelApiParams.Companion.MAVEN_PRIVATE_KEY
import com.sunmarvel.di.MarvelApiParams.Companion.MAVEN_PUBLIC_KEY
import com.sunmarvel.di.MarvelApiParams.Companion.TS
import com.sunmarvel.network.ktor.AppHttpClientBuilder
import com.sunmarvel.network.ktor.AuthType
import com.sunmarvel.network.ktor.RequestHandler
import com.sunmarvel.storage.sharedpreference.EncryptedSharedPreference
import com.sunmarvel.network.ktor.Scope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import kotlinx.coroutines.runBlocking
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideHttpClientBuilder(): AppHttpClientBuilder {
        return AppHttpClientBuilder()
    }

    @Provides
    @Named(Scope.NONE)
    fun provideHttpClient(
        encryptedSharedPreference: EncryptedSharedPreference
    ): HttpClient {
        return provideHttpClientBuilder()
            .protocol(URLProtocol.HTTPS)
            .host(BuildConfig.BASE_URL_HOST)
            .headers(
                listOf(
                    (HttpHeaders.ContentType to "application/json; charset=utf-8"),
                    (HttpHeaders.AcceptLanguage to "en"),
                    (HttpHeaders.Accept to "application/json"),
                    (HttpHeaders.ContentEncoding to "gzip"),
                    (HttpHeaders.Connection to "keep-alive"),
                )
            )
            .queryParameters(
                //MarvelApiQueryParams.get()
                runBlocking {
                    getAuthParams(encryptedSharedPreference)
                }
            )
            .authType(AuthType.NONE)
            .build()
    }

    @Provides
    @Named(Scope.NONE)
    fun provideRequestHandler(
        @Named(Scope.NONE) client: HttpClient,
    ): RequestHandler {
        return RequestHandler(client)
    }

    private suspend fun getAuthParams(encryptedSharedPreference: EncryptedSharedPreference): List<Pair<String, String>> {
        val privateApiKey = encryptedSharedPreference.read(MAVEN_PRIVATE_KEY, "")
        val publicApiKey = encryptedSharedPreference.read(MAVEN_PUBLIC_KEY, "")
        val timeStamp = System.currentTimeMillis().toString()
        val hash = calculateHash(timeStamp, privateApiKey, publicApiKey)

        return listOf(
            (TS to timeStamp),
            (APIKEY to publicApiKey),
            (HASH to hash)
        )
    }

    private fun calculateHash(ts: String, privateKey: String, publicKey: String): String {
        val input = ts + privateKey + publicKey
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

}

class MarvelApiParams {
    companion object {
        const val TS = "ts"
        const val APIKEY = "apikey"
        const val HASH = "hash"

        const val MAVEN_PRIVATE_KEY = "mavenPrivateKey"
        const val MAVEN_PUBLIC_KEY = "mavenPublicKey"

       /* fun get(): List<Pair<String, String>> {
            return listOf(
                (TS to "202503302030"),
                (APIKEY to BuildConfig.MARVEL_PUBLIC_KEY),
                (HASH to "d3dbc8bc9ee380a91bf0d0283aa8bdf3")
            )
        }*/
    }
}