package com.ktnotes.feature.notes.model

import com.ktnotes.model.Response
import kotlinx.serialization.Serializable

@Serializable
data class NoteResponse(val result: Note) : Response

@Serializable
data class NotesResponse(val result: List<Note>) : Response

