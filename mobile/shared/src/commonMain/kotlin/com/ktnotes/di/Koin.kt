package com.ktnotes.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin() {
        appDeclaration()
        modules(
            networkModule(enableNetworkLogs = enableNetworkLogs),
            platformModule()
        )
    }

