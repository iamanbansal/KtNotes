package com.ktnotes.feature.note.presentation

import com.ktnotes.feature.note.model.Note

data class NotesUiState(
    val isLoading: Boolean = false,
    val notes: List<Note> = emptyList(),
    val error: String? = null,
    val isUserLoggedIn: Boolean? = null
)

