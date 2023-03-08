package com.ktnotes.feature.notes.request

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val title: String,
    val note: String
)