package com.ktnotes.db.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime


object RefreshTokenTable : Table("RefreshTokens") {
    val user = reference("userId", UserTable)
    val refreshToken = text("refreshToken")
    val expiresAt = datetime("expiresAt")
}