package com.ktnotes.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(enableNetworkLogs: Boolean = false, appModule:Module) =
    startKoin() {
        modules(
            appModule,
            networkModule(enableNetworkLogs = enableNetworkLogs),
            platformModule()
        )
    }


