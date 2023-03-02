package com.ktnotes

import com.ktnotes.db.DBConfig
import com.ktnotes.feature.auth.UserDaoImpl
import io.ktor.server.application.*
import com.ktnotes.plugins.*
import com.ktnotes.security.token.TokenConfig
import com.ktnotes.security.token.JWTServiceImp
import io.ktor.server.config.ApplicationConfig

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val config = getTokenConfig(this.environment.config)
    val userDao = UserDaoImpl()
    val tokenService = JWTServiceImp(config, userDao)
    val dbConfig = getDBConfig(this.environment.config)

    configureSerialization()
    configureDatabases(dbConfig)
    configureSecurity(tokenService)
    configureRouting(userDao, tokenService)
}


fun getTokenConfig(config: ApplicationConfig): TokenConfig {
    val jwtAudience = config.property("jwt.audience").getString()
    val secret = config.property("key.secret").getString()
    val domain = config.property("jwt.domain").getString()
    val realm = config.property("jwt.realm").getString()
    return TokenConfig(domain, jwtAudience, realm, secret)

}

fun getDBConfig(config: ApplicationConfig): DBConfig {
    val host = config.property("database.host").getString()
    val port = config.property("database.port").getString()
    val name = config.property("database.name").getString()
    val user = config.property("database.user").getString()
    val password = config.property("database.password").getString()

    return DBConfig(host, port, name, user, password)
}