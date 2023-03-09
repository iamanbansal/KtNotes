package com.ktnotes.di.components

import com.ktnotes.di.modules.NoteModule
import com.ktnotes.feature.notes.NotesController

interface NoteComponent {

    fun getNotesController(): Lazy<NotesController>
}

class NoteComponentImpl(private val applicationComponent: ApplicationComponent) : NoteComponent {

    private val notesDao by lazy { NoteModule.provideUserDao() }
    private val notesController = lazy {
        NotesController(notesDao)
    }

    override fun getNotesController(): Lazy<NotesController> {
        return notesController
    }
}
