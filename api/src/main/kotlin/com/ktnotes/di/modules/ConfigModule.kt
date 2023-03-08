package com.ktnotes.di.modules

import com.ktnotes.db.DBConfig
import com.ktnotes.security.token.TokenConfig
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig

object ConfigModule {

    fun getApplicationConfig(application: Application) = application.environment.config

    fun provideDatabaseConfig(config: ApplicationConfig): DBConfig {
        val host = config.property("database.host").getString()
        val port = config.property("database.port").getString()
        val name = config.property("database.name").getString()
        val user = config.property("database.user").getString()
        val password = config.property("database.password").getString()
        return DBConfig(host, port, name, user, password)
    }

    fun provideTokenConfig(config: ApplicationConfig): TokenConfig {
        val jwtAudience = config.property("jwt.audience").getString()
        val secret = config.property("key.secret").getString()
        val domain = config.property("jwt.domain").getString()
        val realm = config.property("jwt.realm").getString()
        return TokenConfig(domain, jwtAudience, realm, secret)
    }
}