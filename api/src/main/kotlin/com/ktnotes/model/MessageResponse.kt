package com.ktnotes.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(private val message: String) : Response