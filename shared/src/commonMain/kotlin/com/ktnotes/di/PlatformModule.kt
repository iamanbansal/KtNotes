package com.ktnotes.di

import org.koin.core.module.Module


const val PREFERENCE_NAME = "KtNotesPreferences"
const val DB_NAME = "ktnotes.db"

expect fun platformModule():Module