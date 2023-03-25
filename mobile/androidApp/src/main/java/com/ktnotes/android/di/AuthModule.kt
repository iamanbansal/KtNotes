package com.ktnotes.android.di

import com.ktnotes.di.koin
import com.ktnotes.feature.auth.remote.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    fun getAuthRepository(): AuthRepository = koin()
}