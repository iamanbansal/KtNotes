package com.ktnotes.feature.auth.model

import com.ktnotes.model.Response
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(val token: String, val refreshToken: String) : Response
