package com.ktnotes.feature.auth.request

import kotlinx.serialization.Serializable
import java.util.EnumMap

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)