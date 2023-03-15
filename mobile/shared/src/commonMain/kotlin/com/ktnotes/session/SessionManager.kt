package com.ktnotes.session

import com.russhwolf.settings.Settings


interface SessionManager {
    fun saveTokens(tokenPair: TokenPair)
    fun getToken(): String
    fun getRefreshToken(): String
    fun getTokenPair(): TokenPair
}

class SessionManagerImpl(private val settings: Settings) : SessionManager {

    override fun saveTokens(tokenPair: TokenPair) {
        settings.putString(AUTH_TOKEN_KEY, tokenPair.accessToken)
    }

    override fun getToken(): String = settings.getString(AUTH_TOKEN_KEY, "")

    override fun getRefreshToken(): String = settings.getString(REFRESH_TOKEN_KEY, "")

    override fun getTokenPair(): TokenPair {
        return TokenPair(getToken(), getRefreshToken())
    }

    companion object {
        private const val AUTH_TOKEN_KEY = "AuthToken"
        private const val REFRESH_TOKEN_KEY = "RefreshToken"
    }
}