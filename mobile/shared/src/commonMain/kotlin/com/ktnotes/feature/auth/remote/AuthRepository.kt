package com.ktnotes.feature.auth.remote

import com.ktnotes.feature.auth.model.AuthRequest
import com.ktnotes.feature.auth.model.AuthResponse
import com.ktnotes.feature.auth.model.RefreshTokenRequest
import com.ktnotes.session.SessionManager
import com.ktnotes.session.TokenPair


interface AuthRepository {
    suspend fun login(authRequest: AuthRequest)
    suspend fun register(authRequest: AuthRequest)
    suspend fun isLoggedIn():Boolean

}

class AuthRepositoryImpl(private val api: AuthApi, private val sessionManager: SessionManager) : AuthRepository {

    override suspend fun login(authRequest: AuthRequest) {
        val response = api.login(authRequest)
        saveToken(response)
    }

    override suspend fun register(authRequest: AuthRequest) {
        val response = api.register(authRequest)
        saveToken(response)
    }

    override suspend fun isLoggedIn(): Boolean {
        val token = sessionManager.getToken()
        return token.isNotBlank()
    }

    private fun saveToken(authResponse: AuthResponse) {
        sessionManager.saveTokens(TokenPair(authResponse.token, authResponse.refreshToken))
    }
}
