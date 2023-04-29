package com.ktnotes.di

import com.ktnotes.database.KtNotesDatabase
import com.ktnotes.session.SessionManager
import com.ktnotes.session.SessionManagerImpl
import org.koin.dsl.module

fun sharedAppModule() = module {
    single<SessionManager> { SessionManagerImpl(get()) }
    single { KtNotesDatabase(get()) }
}