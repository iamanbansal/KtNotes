package com.ktnotes.di

import com.ktnotes.feature.auth.remote.AuthApi
import com.ktnotes.feature.auth.remote.AuthApiImpl
import com.ktnotes.feature.auth.remote.AuthRepository
import com.ktnotes.feature.auth.remote.AuthRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


fun authModule() = module {
  singleOf(::AuthRepositoryImpl){bind<AuthRepository>()}
  singleOf(::AuthApiImpl){bind<AuthApi>()}
}