package com.ktnotes.feature.auth.model


import kotlinx.serialization.Serializable


@Serializable
data class AuthRequest(
    val email: String,
    val password: String,
    val name: String? = null
)

@Serializable
data class AuthResponse(
    val token: String,
    val refreshToken: String
)

@Serializable
class RefreshTokenRequest(val refreshToken: String)
