package com.ktnotes.feature.note.details

import com.ktnotes.feature.note.data.NotesService
import com.ktnotes.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val NEW_NOTE_ID = "new"

open class NoteDetailsSharedViewModel(
    private val noteRepository: NotesService
) : ViewModel() {
    private val _noteState = MutableStateFlow(NoteDetailsUiState())
    val noteState = _noteState.asStateFlow()

    private var existingNoteId: String? = null

    fun getNoteDetails(id: String) {
        if (id == NEW_NOTE_ID || id.isBlank()) return
        existingNoteId = id

        viewModelScope.launch {
            noteRepository.getNoteById(id)?.let { note ->
                _noteState.update { NoteDetailsUiState(note = note) }
            }
        }
    }

    fun saveNote(title: String, content: String) {
        _noteState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            runCatching {
                if (existingNoteId != null) {
                    noteState.value.note?.let {
                        noteRepository.updateNote(
                            it.copy(title = title, note = content)
                        )
                    }
                } else {
//                    noteRepository.insertNote(title = title, note = content)
                }
            }.onSuccess {
                _noteState.update { it.copy(isLoading = false, finishSaving = true) }
            }.onFailure {
                _noteState.update { it.copy(isLoading = false, error = "Failed saving note") }
            }
        }
    }

}