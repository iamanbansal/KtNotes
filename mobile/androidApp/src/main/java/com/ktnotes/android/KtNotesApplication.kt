package com.ktnotes.android

import android.app.Application
import android.content.Context
import com.ktnotes.di.initKoin
import org.koin.dsl.module

class KtNotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(BuildConfig.DEBUG) {
            modules(
                module {
                    single<Context> { this@KtNotesApplication }
                }
            )
        }
    }
}