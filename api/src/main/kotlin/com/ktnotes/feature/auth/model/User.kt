package com.ktnotes.feature.auth.model

import com.ktnotes.entity.UserTable
import org.jetbrains.exposed.sql.ResultRow

data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val salt: String
) {
    companion object {
        fun fromResultRow(row: ResultRow): User {
            return User(
                row[UserTable.id].value.toString(),
                row[UserTable.name],
                row[UserTable.email],
                row[UserTable.password],
                row[UserTable.salt]
            )
        }
    }
}