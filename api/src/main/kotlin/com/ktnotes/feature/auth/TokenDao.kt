package com.ktnotes.feature.auth

import com.ktnotes.db.entity.RefreshTokenTable
import com.ktnotes.feature.auth.model.RefreshToken
import com.ktnotes.feature.auth.model.TokenPair
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

interface TokenDao {
    fun saveRefreshToken(token: RefreshToken)
    fun getRefreshTokenFromDb(refreshToken: String): RefreshToken?
    fun updateToken(userId: String, pair: TokenPair)
}

class TokenDaoImpl : TokenDao {
    override fun saveRefreshToken(token: RefreshToken) {
        transaction {
            RefreshTokenTable.insert {
                it[user] = UUID.fromString(token.userId)
                it[refreshToken] = token.refreshToken
                it[expiresAt] = token.expiresAt
            }
        }
    }

    override fun getRefreshTokenFromDb(refreshToken: String): RefreshToken? = transaction {
        RefreshTokenTable.select { RefreshTokenTable.refreshToken eq refreshToken }
            .map { RefreshToken.fromResultRow(it) }.firstOrNull()
    }

    override fun updateToken(userId: String, pair: TokenPair) {
        transaction {
            RefreshTokenTable.update({
                RefreshTokenTable.user eq UUID.fromString(userId)
            }) {
                it[refreshToken] = pair.refreshToken
                it[expiresAt] = pair.expiresAt
            }
        }
    }

}