package com.ktnotes.session

import com.russhwolf.settings.Settings


interface SessionManager {
    fun saveToken(token: String)
    fun getToken(): String
}

class SessionManagerImpl(private val settings: Settings) : SessionManager {

    override fun saveToken(token: String) {
        settings.putString(AUTH_TOKEN_KEY, token)
    }

    override fun getToken(): String = settings.getString(AUTH_TOKEN_KEY, "")

    companion object {
        private const val AUTH_TOKEN_KEY = "AuthToken"
    }
}