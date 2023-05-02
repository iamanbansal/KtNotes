package com.ktnotes.android.feature.note

import com.ktnotes.feature.note.data.NotesService
import com.ktnotes.feature.note.presentation.NotesSharedViewModel
import com.ktnotes.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NotesService,
    private val sessionManager: SessionManager
) : NotesSharedViewModel(noteRepository, sessionManager) {

}