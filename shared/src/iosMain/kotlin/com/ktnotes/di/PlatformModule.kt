package com.ktnotes.di

import com.ktnotes.database.KtNotesDatabase
import com.ktnotes.feature.auth.presentation.AuthSharedViewModel
import com.ktnotes.feature.note.details.NoteDetailsSharedViewModel
import com.ktnotes.feature.note.presentation.NotesSharedViewModel
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual fun platformModule(): Module = module {
    single { Darwin.create() }
    single { getNSUDefaults() }
    single { getNativeSqliteDriver() }
}

private fun getNSUDefaults(): Settings = NSUserDefaultsSettings(NSUserDefaults(PREFERENCE_NAME))

private fun getNativeSqliteDriver(): SqlDriver =
    NativeSqliteDriver(KtNotesDatabase.Schema, DB_NAME)
