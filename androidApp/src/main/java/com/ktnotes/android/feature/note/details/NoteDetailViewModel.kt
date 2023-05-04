package com.ktnotes.android.feature.note.details

import com.ktnotes.feature.note.data.NotesService
import com.ktnotes.feature.note.details.NoteDetailsSharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    noteRepository: NotesService
) : NoteDetailsSharedViewModel(noteRepository) {

    private val _fieldState = MutableStateFlow(NoteFieldsState())
    val fieldState = _fieldState.asStateFlow()

    init {
        noteState.onEach { details ->
            _fieldState.update {
                NoteFieldsState(
                    title = details.note?.title ?: "",
                    content = details.note?.note ?: ""
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onNoteTitleChanged(title: String) {
        _fieldState.update { it.copy(title = title) }

    }

    fun onNoteContentChanged(content: String) {
        _fieldState.update { it.copy(content = content) }
    }

    fun saveNote() {
        saveNote(fieldState.value.title, fieldState.value.content)
    }
}