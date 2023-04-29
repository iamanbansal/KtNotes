package com.ktnotes.di

import com.ktnotes.exception.BadRequestException
import com.ktnotes.feature.auth.model.AuthResponse
import com.ktnotes.feature.auth.model.RefreshTokenRequest
import com.ktnotes.helper.postWithJsonContent
import com.ktnotes.model.MessageResponse
import com.ktnotes.session.SessionManager
import com.ktnotes.session.TokenPair
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun networkModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), get(), get(), enableNetworkLogs = enableNetworkLogs) }
}

fun createJson() = Json {
    isLenient = true; ignoreUnknownKeys = true;
}

fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    sessionManager: SessionManager,
    enableNetworkLogs: Boolean
): HttpClient {
    val client = HttpClient(httpClientEngine) {

        install(ContentNegotiation) {
            json(json)
        }

        defaultRequest {
            url(BASE_URL)
        }

        //cache token for authenticated apis
        val bearerTokenStorage = mutableListOf<BearerTokens>()

        install(Auth) {
            bearer {
                loadTokens {
                    //in case of login or signup api, it will load blank tokens
                    if (bearerTokenStorage.isEmpty()) {
                        val tokenPair = sessionManager.getTokenPair()
                        bearerTokenStorage.add(
                            BearerTokens(tokenPair.accessToken, tokenPair.refreshToken)
                        )
                    }
                    bearerTokenStorage.last()
                }
                refreshTokens {
                    //after immediate login or signup api, this will load blank tokens
                    //so if check if blank or null token are there load tokens from settings
                    //if the loaded token is blank or null, mean user is trying to access without login
                    //so refresh api will throw BadRequest Exception

                    val refreshToken = if (oldTokens?.refreshToken.isNullOrBlank().not())
                        oldTokens!!.refreshToken
                    else
                        sessionManager.getRefreshToken()

                    //create new client for retry api
                    val retryClient =
                        createRetryHttpClient(httpClientEngine, json, enableNetworkLogs)

                    try {
                        val response = retryClient.postWithJsonContent("auth/refresh") {
                            setBody(RefreshTokenRequest(refreshToken))
                        }.body<AuthResponse>()
                        sessionManager.saveTokens(TokenPair(response.token, response.refreshToken))

                        bearerTokenStorage.add(BearerTokens(response.token, response.refreshToken))

                    } catch (e: Exception) {
                        println(e)
                    }
                    bearerTokenStorage.last()
                }
            }
        }

        HttpResponseValidator {
            validateResponse { response: HttpResponse ->
                if (response.status == HttpStatusCode.BadRequest && response.contentType() == ContentType.Application.Json) {
                    val messageResponse = Json.decodeFromString<MessageResponse>(response.body())
                    throw BadRequestException(messageResponse.message)
                }
            }
        }

        if (enableNetworkLogs) {
            install(Logging) {
                logger = SIMPLE
                level = LogLevel.ALL
            }
        }
    }
    return client
}

fun createRetryHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    enableNetworkLogs: Boolean
): HttpClient {
    val client = HttpClient(httpClientEngine) {

        install(ContentNegotiation) {
            json(json)
        }

        defaultRequest {
            url(BASE_URL)
        }
        if (enableNetworkLogs) {
            install(Logging) {
                logger = SIMPLE
                level = LogLevel.ALL
            }
        }
    }
    return client
}

private val SIMPLE = object : Logger {
    override fun log(message: String) {
        println(message)
    }
}

const val BASE_URL = "https://ktnotes-production.up.railway.app"