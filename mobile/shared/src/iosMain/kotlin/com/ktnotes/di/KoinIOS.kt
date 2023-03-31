package com.ktnotes.di

import com.ktnotes.feature.auth.presentation.AuthCallbackViewModel
import com.ktnotes.feature.note.NotesCallbackViewModel
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
}