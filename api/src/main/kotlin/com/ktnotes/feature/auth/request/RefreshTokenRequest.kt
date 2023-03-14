package com.ktnotes.feature.auth.request

import kotlinx.serialization.Serializable

@Serializable
class RefreshTokenRequest(val refreshToken:String)