package com.ktnotes.entity

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow


object UserTable : UUIDTable("User") {
    val name = varchar("name", length = 50)
    val email = varchar("username", length = 30)
    val password = text("password")
    val salt = text("salt")
}