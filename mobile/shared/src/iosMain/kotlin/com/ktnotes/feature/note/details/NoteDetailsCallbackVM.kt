package com.ktnotes.feature.note.details

import com.ktnotes.feature.note.data.NoteRepository
import com.ktnotes.viewmodel.asCallbacks

class NoteDetailsCallbackVM(noteRepository: NoteRepository) {
    private val viewModel = NoteDetailsSharedViewModel(noteRepository)

    val stateAdapter = viewModel.noteState.asCallbacks(viewModel.viewModelScope)

    fun getNoteDetails(id: String) {
        viewModel.getNoteDetails(id)
    }

    fun saveNote(title: String, content: String) {
        viewModel.saveNote(title, content)
    }

    fun clear() {
        viewModel.clear()
    }
}