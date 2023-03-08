package com.ktnotes.di.modules

import com.ktnotes.feature.notes.NotesDao
import com.ktnotes.feature.notes.NotesDaoImp

object NoteModule {

    fun provideUserDao(): NotesDao = NotesDaoImp()
}