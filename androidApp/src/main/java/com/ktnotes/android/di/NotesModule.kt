package com.ktnotes.android.di

import com.ktnotes.di.koin
import com.ktnotes.feature.note.data.NoteRepository
import com.ktnotes.feature.note.data.NotesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object NotesModule {

    @Provides
    fun getNoteRepository(): NoteRepository = koin()

    @Provides
    fun getNoteService(): NotesService = koin()

}