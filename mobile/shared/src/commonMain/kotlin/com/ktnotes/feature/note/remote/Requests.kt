package com.ktnotes.feature.note.remote

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(val title: String, val note: String)

@Serializable
data class PinRequest(val isPinned: Boolean)
