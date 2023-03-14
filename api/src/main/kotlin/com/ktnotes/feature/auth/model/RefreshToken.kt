package com.ktnotes.feature.auth.model

import com.ktnotes.db.entity.RefreshTokenTable
import org.jetbrains.exposed.sql.ResultRow
import java.time.LocalDateTime

data class RefreshToken(
    val userId: String, val refreshToken: String, val expiresAt: LocalDateTime
) {
    companion object {
        fun fromResultRow(row: ResultRow): RefreshToken {
            return RefreshToken(
                row[RefreshTokenTable.user].toString(),
                row[RefreshTokenTable.refreshToken],
                row[RefreshTokenTable.expiresAt],
            )
        }
    }
}

data class TokenPair(val accessToken:String, val refreshToken: String, val expiresAt: LocalDateTime)