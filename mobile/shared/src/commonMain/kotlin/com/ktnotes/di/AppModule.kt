package com.ktnotes.di

import com.ktnotes.session.SessionManager
import com.ktnotes.session.SessionManagerImpl
import org.koin.dsl.module

fun sharedAppModule() = module {
    single<SessionManager> { SessionManagerImpl(get()) }
}