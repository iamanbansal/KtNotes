package com.ktnotes.di

import org.koin.core.context.startKoin

fun initKoin(enableNetworkLogs: Boolean = false) =
    startKoin() {
        modules(
            networkModule(enableNetworkLogs = enableNetworkLogs),
            platformModule()
        )
    }


