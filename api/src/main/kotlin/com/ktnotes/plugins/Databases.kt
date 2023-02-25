package com.ktnotes.plugins

import com.ktnotes.entity.User
import org.jetbrains.exposed.sql.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {

    val config = this.environment.config
    val host = config.property("database.host").getString()
    val port = config.property("database.port").getString()
    val name = config.property("database.name").getString()
    val user = config.property("database.user").getString()
    val password = config.property("database.password").getString()

    val database = Database.connect(
        url = "jdbc:postgresql://$host:$port/$name",
        user = user,
        driver = "org.postgresql.Driver",
        password = password
    )

    data class Data(val name:String, val sdf:String)
    transaction {
        SchemaUtils.create(User)
    }
}