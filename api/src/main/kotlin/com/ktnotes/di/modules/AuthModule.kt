package com.ktnotes.di.modules

import com.ktnotes.feature.auth.AuthController
import com.ktnotes.feature.auth.UserDao
import com.ktnotes.feature.auth.UserDaoImpl
import com.ktnotes.security.hashing.BCryptHashingService
import com.ktnotes.security.hashing.HashingService
import com.ktnotes.security.token.JWTServiceImp
import com.ktnotes.security.token.TokenConfig
import com.ktnotes.security.token.TokenService

object AuthModule {

    fun provideUserDao(): UserDao = UserDaoImpl()

    fun provideHashingService(): HashingService {
        return BCryptHashingService()
    }

    fun provideTokenService(tokenConfig: TokenConfig, userDao: UserDao): TokenService {
        return JWTServiceImp(tokenConfig, userDao)
    }

    fun provideAuthController(
        userDao: UserDao,
        tokenService: TokenService,
        hashingService: HashingService
    ): AuthController {
        return AuthController(userDao, tokenService, hashingService)
    }
}