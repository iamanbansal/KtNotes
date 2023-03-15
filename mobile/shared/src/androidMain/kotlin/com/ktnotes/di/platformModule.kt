package com.ktnotes.di

import android.content.Context
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.engine.android.Android
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { Android.create() }
    single { getSharedPreference(get()) }
}

fun getSharedPreference(appContext: Context): SharedPreferencesSettings {
    val delegate = appContext.getSharedPreferences("KtNotesPreferences", Context.MODE_PRIVATE)
    return SharedPreferencesSettings(delegate)
}