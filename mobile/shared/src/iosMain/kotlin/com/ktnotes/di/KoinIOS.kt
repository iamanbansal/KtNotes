package com.ktnotes.di

import com.ktnotes.feature.auth.presentation.AuthCallbackViewModel
import com.ktnotes.feature.note.NotesCallbackViewModel
import com.ktnotes.feature.note.details.NoteDetailsCallbackVM
import com.ktnotes.feature.note.details.NoteDetailsSharedViewModel
import org.koin.dsl.module

fun initKoin() {
    initKoin(false,
        module {

        }
    )
}


object KotlinDependencies {
    //to get from shared koin dependencies
    fun getCallbackAuthViewModel(): AuthCallbackViewModel = koin()
    fun getNotesViewModel(): NotesCallbackViewModel = koin()
    fun getNoteDetailsViewModel(): NoteDetailsCallbackVM = koin()
}