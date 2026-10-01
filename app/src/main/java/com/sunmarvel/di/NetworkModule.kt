package com.sunmarvel.di

import com.sunmarvel.network.ktor.AppHttpClientBuilder
import com.sunmarvel.network.ktor.AuthType
import com.sunmarvel.network.ktor.RequestHandler
import com.sunmarvel.network.ktor.Scope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideHttpClientBuilder(): AppHttpClientBuilder = AppHttpClientBuilder()

    @Provides
    @Named(Scope.NONE)
    fun provideHttpClient(): HttpClient =
        provideHttpClientBuilder()
            .protocol(URLProtocol.HTTPS)
            .host("akabab.github.io")
            .headers(
                listOf(
                    HttpHeaders.ContentType to "application/json; charset=utf-8",
                    HttpHeaders.AcceptLanguage to "en",
                    HttpHeaders.Accept to "application/json",
                )
            )
            .authType(AuthType.NONE)
            .build()

    @Provides
    @Named(Scope.NONE)
    fun provideRequestHandler(@Named(Scope.NONE) client: HttpClient): RequestHandler =
        RequestHandler(client)

    @Provides
    @Named("openLibrary")
    fun provideOpenLibraryClient(): HttpClient =
        provideHttpClientBuilder()
            .protocol(URLProtocol.HTTPS)
            .host("openlibrary.org")
            .headers(
                listOf(
                    HttpHeaders.ContentType to "application/json; charset=utf-8",
                    HttpHeaders.Accept to "application/json",
                )
            )
            .authType(AuthType.NONE)
            .build()

    @Provides
    @Named("openLibrary")
    fun provideOpenLibraryRequestHandler(
        @Named("openLibrary") client: HttpClient
    ): RequestHandler = RequestHandler(client)
}
