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
        syncNotes()
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

    private fun syncNotes() {
        viewModelScope.launch {
            runCatching {
                noteRepository.getNotesFromRemote()
            }.onFailure {
                _notesState.update { it.copy(isLoading = false,error = "Failed to sync notes") }
            }
        }
    }

    fun deleteNote(id: String) {
        _notesState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            runCatching {
                noteRepository.deleteNoteById(id)
            }.onFailure {
                _notesState.update {
                    it.copy(isLoading = false,error = "Failed to delete message")//todo figure out get string in kmm way
                }
            }
        }
    }

    fun logout(){
        _notesState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            sessionManager.clearSession()
            noteRepository.clearAll()
            _notesState.update { NotesUiState(isUserLoggedIn = false) }
        }
    }

    private fun isLoggedIn() {
        _notesState.update {
            NotesUiState(
                isUserLoggedIn = sessionManager.getToken().isNotBlank()
            )
        }
    }

    fun messageShown() {
        _notesState.update {
            it.copy(error = null)
        }
    }

}
