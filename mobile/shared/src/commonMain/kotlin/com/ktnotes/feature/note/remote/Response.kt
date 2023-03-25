package com.ktnotes.feature.note.remote

import com.ktnotes.feature.note.model.Note
import kotlinx.serialization.Serializable

@Serializable
data class NoteResponse(
    val result: Note
)

@Serializable
data class NotesResponse(
    val result: List<Note>
)

