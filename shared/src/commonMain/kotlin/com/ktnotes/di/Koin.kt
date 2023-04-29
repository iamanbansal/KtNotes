package com.ktnotes.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(enableNetworkLogs: Boolean = false, appModule: Module) =
    startKoin() {
        modules(
            appModule,
            sharedAppModule(),
            networkModule(enableNetworkLogs = enableNetworkLogs),
            platformModule(),
            authModule(),
            noteModule()
        )
    }
