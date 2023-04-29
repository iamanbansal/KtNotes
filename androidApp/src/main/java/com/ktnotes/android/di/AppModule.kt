package com.ktnotes.android.di

import com.ktnotes.di.koin
import com.ktnotes.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun getSessionManager(): SessionManager = koin()
}