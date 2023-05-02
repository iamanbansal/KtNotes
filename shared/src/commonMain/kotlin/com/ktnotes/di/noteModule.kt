package com.ktnotes.di

import com.ktnotes.feature.note.data.NoteRepository
import com.ktnotes.feature.note.data.NoteRepositoryImpl
import com.ktnotes.feature.note.data.NoteUnsyncedService
import com.ktnotes.feature.note.data.NoteUnsyncedServiceImpl
import com.ktnotes.feature.note.data.NotesService
import com.ktnotes.feature.note.data.NotesServiceImpl
import com.ktnotes.feature.note.db.NoteQueueDao
import com.ktnotes.feature.note.db.NoteQueueDaoImpl
import com.ktnotes.feature.note.db.NotesDao
import com.ktnotes.feature.note.db.NotesDaoImpl
import com.ktnotes.feature.note.details.NoteDetailsSharedViewModel
import com.ktnotes.feature.note.presentation.NotesSharedViewModel
import com.ktnotes.feature.note.remote.NotesApi
import com.ktnotes.feature.note.remote.NotesApiImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun noteModule() = module {
    singleOf(::NotesDaoImpl) { bind<NotesDao>() }
    singleOf(::NoteQueueDaoImpl) { bind<NoteQueueDao>() }
    singleOf(::NotesApiImpl) { bind<NotesApi>() }
    singleOf(::NoteRepositoryImpl) { bind<NoteRepository>() }
    singleOf(::NotesServiceImpl) { bind<NotesService>() }
    singleOf(::NoteUnsyncedServiceImpl) { bind<NoteUnsyncedService>() }
    factoryOf(::NotesSharedViewModel)
    factoryOf(::NoteDetailsSharedViewModel)
}