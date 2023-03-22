package com.ktnotes.di

import com.ktnotes.feature.note.db.NotesDao
import com.ktnotes.feature.note.db.NotesDaoImpl
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun noteModule() = module {
    factoryOf(::NotesDaoImpl) { bind<NotesDao>() }
}