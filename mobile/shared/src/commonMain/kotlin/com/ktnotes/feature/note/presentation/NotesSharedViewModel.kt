package com.ktnotes.feature.note.presentation

import com.ktnotes.feature.note.data.NoteRepository
import com.ktnotes.session.SessionManager
import com.ktnotes.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class NotesSharedViewModel(
    private val noteRepository: NoteRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _notesState = MutableStateFlow(NotesUiState(isLoading = true))
    val notesState = _notesState.asStateFlow()

    init {
        isLoggedIn()
        observerNotes()
    }

    private fun observerNotes() {
        if (_notesState.value.isUserLoggedIn == false) return

        viewModelScope.launch {
            noteRepository.getAllNotes().collect { notes ->
                _notesState.update {
                    NotesUiState(notes = notes)
                }
            }
        }
    }

    fun deleteNote(id: String) {
        _notesState.update { NotesUiState(isLoading = true) }

        viewModelScope.launch {
            noteRepository.deleteNoteById(id)
        }
    }

    private fun isLoggedIn() {
        _notesState.update {
            NotesUiState(
                isUserLoggedIn = sessionManager.getToken().isNotBlank()
            )
        }
    }

}
