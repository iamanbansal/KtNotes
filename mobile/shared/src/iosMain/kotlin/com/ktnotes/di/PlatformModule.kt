package com.ktnotes.di

import com.ktnotes.feature.auth.presentation.AuthCallbackViewModel
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual fun platformModule():Module = module {
    single { Darwin.create() }
    single <Settings>{ NSUserDefaultsSettings(getNSUDefaults()) }
    factory { AuthCallbackViewModel(get()) }
}

private fun getNSUDefaults() =  NSUserDefaults(PREFERENCE_NAME)