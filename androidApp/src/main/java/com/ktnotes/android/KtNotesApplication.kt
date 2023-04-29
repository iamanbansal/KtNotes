package com.ktnotes.android

import android.app.Application
import android.content.Context
import com.ktnotes.di.initKoin
import dagger.hilt.android.HiltAndroidApp
import org.koin.dsl.module


@HiltAndroidApp
class KtNotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(BuildConfig.DEBUG, appModule = module {
            single<Context> { this@KtNotesApplication }
        })
    }
}