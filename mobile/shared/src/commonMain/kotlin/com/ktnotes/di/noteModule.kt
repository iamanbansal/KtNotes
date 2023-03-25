package com.ktnotes.di

import com.ktnotes.feature.note.data.NoteRepository
import com.ktnotes.feature.note.data.NoteRepositoryImpl
import com.ktnotes.feature.note.db.NotesDao
import com.ktnotes.feature.note.remote.NotesApi
import com.ktnotes.feature.note.remote.NotesApiImpl
import com.ktnotes.feature.note.db.NotesDaoImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun noteModule() = module {
    factoryOf(::NotesDaoImpl) { bind<NotesDao>() }
    factoryOf(::NotesApiImpl) { bind<NotesApi>() }
    factoryOf(::NoteRepositoryImpl) { bind<NoteRepository>() }
}