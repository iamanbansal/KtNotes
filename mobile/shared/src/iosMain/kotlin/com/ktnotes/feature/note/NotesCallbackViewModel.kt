package com.ktnotes.feature.note

import com.ktnotes.feature.note.data.NoteRepository
import com.ktnotes.feature.note.presentation.NotesSharedViewModel
import com.ktnotes.session.SessionManager
import com.ktnotes.viewmodel.asCallbacks

class NotesCallbackViewModel(noteRepository: NoteRepository, sessionManager: SessionManager) {

    private val viewModel = NotesSharedViewModel(noteRepository, sessionManager)

    val stateAdapter = viewModel.notesState.asCallbacks(viewModel.viewModelScope)
    fun deleteNote(id: String) {
        viewModel.deleteNote(id)
    }

    fun logout() {
        viewModel.logout()
    }

    fun clear() {
        viewModel.clear()
    }
}