package com.ktnotes.feature.auth.remote

import com.ktnotes.feature.auth.model.AuthRequest
import com.ktnotes.feature.auth.model.AuthResponse
import com.ktnotes.feature.auth.model.RefreshTokenRequest
import com.ktnotes.helper.postWithJsonContent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


interface AuthApi {
    suspend fun login(authRequest: AuthRequest): AuthResponse
    suspend fun register(authRequest: AuthRequest): AuthResponse
    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest):AuthResponse
}


class AuthApiImpl(private val httpClient: HttpClient) : AuthApi {

    override suspend fun login(authRequest: AuthRequest): AuthResponse {
        return httpClient.post("auth/login") {
            contentType(ContentType.Application.Json)
            setBody(authRequest)
        }.body()
    }

    override suspend fun register(authRequest: AuthRequest): AuthResponse {
        return httpClient.postWithJsonContent("auth/register") {
            setBody(authRequest)
        }.body()
    }

    override suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): AuthResponse {
        return httpClient.postWithJsonContent("auth/refresh"){
            setBody(refreshTokenRequest)
        }.body()
    }
}
