package com.ktnotes.di

import org.koin.core.component.KoinComponent
import org.koin.dsl.module

fun initKoin() {
    initKoin(false,
        module {

        }
    )
}


object KotlinDependencies : KoinComponent {
    //to get from shared koin dependencies
}