package com.ktnotes.di.components

import com.ktnotes.db.DBConfig
import com.ktnotes.di.modules.ConfigModule

interface DatabaseComponent {
    fun getDBConfig(): DBConfig
}

class DatabaseComponentImpl(private val applicationComponent: ApplicationComponent) : DatabaseComponent {

    private val applicationConfig = ConfigModule.getApplicationConfig(applicationComponent.application)
    private val dbConfig = ConfigModule.provideDatabaseConfig(applicationConfig)

    override fun getDBConfig(): DBConfig {
        return dbConfig
    }
}