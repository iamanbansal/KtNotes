/*
* Error: Duplicate JVM class name 'com/ktnotes/di/PlatformModuleKt'. Happens because of having static function
* Track: https://youtrack.jetbrains.com/issue/KT-21186
*/
@file:JvmName("PlatformModuleJvm")


package com.ktnotes.di


import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.engine.android.Android
import org.koin.core.module.Module
import org.koin.dsl.module


actual fun platformModule(): Module = module {
    single { Android.create() }
    single<Settings> { getSharedPreference(get()) }
}

private fun getSharedPreference(appContext: Context): SharedPreferencesSettings {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    val delegate = EncryptedSharedPreferences.create(
        PREFERENCE_NAME,
        masterKeyAlias,
        appContext,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    return SharedPreferencesSettings(delegate)
}