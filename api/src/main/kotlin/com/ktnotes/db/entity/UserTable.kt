package com.ktnotes.db.entity

import org.jetbrains.exposed.dao.id.UUIDTable


object UserTable : UUIDTable("User") {
    val name = varchar("name", length = 50)
    val email = varchar("username", length = 30)
    val password = text("password")
    val salt = text("salt")
}