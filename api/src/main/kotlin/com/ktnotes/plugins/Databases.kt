package com.ktnotes.plugins

import com.ktnotes.db.DBConfig
import com.ktnotes.entity.UserTable
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases(dbConfig: DBConfig) {

    val database = Database.connect(
        url = "jdbc:postgresql://${dbConfig.host}:${dbConfig.port}/${dbConfig.name}",
        user = dbConfig.user,
        driver = "org.postgresql.Driver",
        password = dbConfig.password
    )

    transaction {
        SchemaUtils.create(UserTable)
    }
}