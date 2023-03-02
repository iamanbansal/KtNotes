package com.ktnotes.feature.auth.model

import com.ktnotes.Response
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(val token: String) : Response
