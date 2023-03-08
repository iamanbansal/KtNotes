package com.ktnotes

import com.ktnotes.di.DIAppComponent
import com.ktnotes.plugins.configureCORS
import com.ktnotes.plugins.configureDatabases
import com.ktnotes.plugins.configureRouting
import com.ktnotes.plugins.configureSecurity
import com.ktnotes.plugins.configureSerialization
import com.ktnotes.plugins.configureStatusPages
import io.ktor.server.application.Application

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    val appComponent = DIAppComponent.builder().withApplication(this).build()
    val databaseConfig = appComponent.getDatabaseComponent().getDBConfig()

    configureSerialization()
    configureDatabases(databaseConfig)
    configureSecurity(appComponent)
    configureRouting(appComponent)
    configureStatusPages()
    configureCORS()
}