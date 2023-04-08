package com.ktnotes.di

import com.ktnotes.feature.auth.presentation.AuthSharedViewModel
import com.ktnotes.feature.note.details.NoteDetailsSharedViewModel
import com.ktnotes.feature.note.presentation.NotesSharedViewModel
import org.koin.dsl.module

fun initKoin() {
    initKoin(false,
        module {

        }
    )
}


object KotlinDependencies {
    //to get from shared koin dependencies
    fun getAuthViewModel(): AuthSharedViewModel = koin()
    fun getNotesViewModel(): NotesSharedViewModel = koin()
    fun getNoteDetailsViewModel(): NoteDetailsSharedViewModel = koin()
}