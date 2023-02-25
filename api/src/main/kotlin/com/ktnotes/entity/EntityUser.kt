package com.ktnotes.entity

import org.jetbrains.exposed.dao.id.UUIDTable


object User : UUIDTable() {
    val name = varchar("name", length = 50)
    val email = varchar("username", length = 30)
    val password = text("password")
}