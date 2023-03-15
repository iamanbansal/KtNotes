package com.ktnotes.di

import com.ktnotes.session.SessionManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
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
const val AUTH_HEADER_KEY = "Authorization"