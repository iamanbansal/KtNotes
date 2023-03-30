package com.ktnotes.feature.note.details

import com.ktnotes.feature.note.model.Note

data class NoteDetailsUiState(
    val isLoading: Boolean = false,
    val note: Note? = null,
    val finishSaving: Boolean = false,
    val error: String? = null
)

