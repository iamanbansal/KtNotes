package com.ktnotes.di.components

import com.ktnotes.di.modules.AuthModule
import com.ktnotes.di.modules.ConfigModule
import com.ktnotes.feature.auth.AuthController
import com.ktnotes.security.token.TokenService

interface AuthComponent {
    fun getAuthController(): Lazy<AuthController>
    fun getTokenService(): TokenService
}

class AuthComponentImpl(private val applicationComponent: ApplicationComponent) : AuthComponent {

    private val userDao by lazy { AuthModule.provideUserDao() }
    private val hashingService by lazy { AuthModule.provideHashingService() }
    private val applicationConfig = ConfigModule.getApplicationConfig(applicationComponent.application)
    private val tokenConfig by lazy { ConfigModule.provideTokenConfig(applicationConfig) }
    private val tokenService = AuthModule.provideTokenService(tokenConfig, userDao)
    private val authController = lazy {
        AuthModule.provideAuthController(userDao, tokenService, hashingService)
    }


    override fun getAuthController(): Lazy<AuthController> {
        return authController
    }

    override fun getTokenService(): TokenService {
        return tokenService
    }
}