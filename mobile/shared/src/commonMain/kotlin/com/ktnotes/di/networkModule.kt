package com.ktnotes.di

import com.ktnotes.feature.auth.model.RefreshTokenRequest
import com.ktnotes.feature.auth.remote.AuthApi
import com.ktnotes.feature.auth.remote.AuthApiImpl
import com.ktnotes.feature.auth.remote.AuthRepository
import com.ktnotes.feature.auth.remote.AuthRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun networkModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), get(), get(), enableNetworkLogs = enableNetworkLogs) }
    singleOf(::AuthApiImpl) { bind<AuthApi>() }
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
}

fun createJson() = Json {
    isLenient = true; ignoreUnknownKeys = true;
}

fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    authRepository: AuthRepository,
    enableNetworkLogs: Boolean
): HttpClient {
    val client = HttpClient(httpClientEngine) {

        install(ContentNegotiation) {
            json(json)
        }

        defaultRequest {
            url(BASE_URL)
        }

        val bearerTokenStorage = mutableListOf<BearerTokens>()

        install(Auth) {
            bearer {
                loadTokens {
                    if (bearerTokenStorage.isEmpty()) {
                        val tokenPair = authRepository.getTokenPair()
                        bearerTokenStorage.add(
                            BearerTokens(tokenPair.accessToken, tokenPair.refreshToken)
                        )
                    }
                    bearerTokenStorage.last()
                }
                refreshTokens {
                    val tokenPair = authRepository.getTokenPair()
                    val response =
                        authRepository.refreshToken(RefreshTokenRequest(tokenPair.refreshToken))

                    bearerTokenStorage.add(
                        BearerTokens(tokenPair.accessToken, tokenPair.refreshToken)
                    )

                    bearerTokenStorage.last()
                }
            }
        }
        if (enableNetworkLogs) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }
    return client
}

const val BASE_URL = "https://ktnotes-production.up.railway.app"