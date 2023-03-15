package com.ktnotes.di

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual fun platformModule():Module = module {
    single { Darwin.create() }
    single <Settings>{ NSUserDefaultsSettings(getNSUDefaults()) }
}

private fun getNSUDefaults() =  NSUserDefaults(PREFERENCE_NAME)