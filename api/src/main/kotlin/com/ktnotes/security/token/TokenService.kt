package com.ktnotes.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.ktnotes.exceptions.BadRequestException
import com.ktnotes.feature.auth.TokenDao
import com.ktnotes.feature.auth.UserDao
import com.ktnotes.feature.auth.model.RefreshToken
import com.ktnotes.feature.auth.model.TokenPair
import com.ktnotes.security.UserPrincipal
import io.ktor.server.auth.Principal
import io.ktor.server.auth.jwt.JWTCredential
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

interface TokenService {
    fun generateToken(vararg claims: TokenClaim): TokenPair
    fun getVerifier(): JWTVerifier
    fun validate(credential: JWTCredential): Principal?
    fun validateRefreshToken(refreshToken: String): TokenPair
    fun insertToken(userid: String, tokenPair: TokenPair)
    fun updateToken(userid: String, tokenPair: TokenPair)
}

class JWTServiceImp(
    private val config: TokenConfig,
    private val userDao: UserDao,
    private val tokenDao: TokenDao
) : TokenService {

    override fun generateToken(vararg claims: TokenClaim): TokenPair {

        val expireAt = LocalDateTime.now().plusDays(TOKEN_ACCESS_DAYS)
        val builder = JWT.create()
            .withIssuer(config.domain)
            .withAudience(config.audience)
            .withExpiresAt(Date.from(expireAt.toInstant(ZoneOffset.UTC)))

        claims.forEach {
            builder.withClaim(it.name, it.value)
        }

        val accessToken = builder.sign(Algorithm.HMAC256(config.secret))
        val refreshToken = UUID.randomUUID().toString()
        return TokenPair(accessToken, refreshToken, expireAt)
    }

    override fun getVerifier(): JWTVerifier {
        return JWT
            .require(Algorithm.HMAC256(config.secret))
            .withAudience(config.audience)
            .withIssuer(config.domain)
            .build()
    }

    override fun validate(credential: JWTCredential): Principal? {
        val userId = credential.getClaim(CLAIM, String::class)
        val user = userDao.findByUUID(UUID.fromString(userId))

        return if (user != null) UserPrincipal(user)
        else null
    }

    override fun validateRefreshToken(token: String): TokenPair {
        val refreshTokenData = tokenDao.getRefreshTokenFromDb(token)

        val currentTime = LocalDateTime.now()
        if (refreshTokenData != null && refreshTokenData.expiresAt.isBefore(currentTime)) {
            val pair = generateToken(TokenClaim(CLAIM, refreshTokenData.userId))
            updateToken(refreshTokenData.userId, pair)
            return pair
        } else {
            throw BadRequestException("Invalid credentials")
        }
    }

    override fun insertToken(userid: String, tokenPair: TokenPair) {
        tokenDao.saveRefreshToken(RefreshToken(userid, tokenPair.refreshToken, tokenPair.expiresAt))
    }

    override fun updateToken(userid: String, tokenPair: TokenPair) {
        tokenDao.updateToken(userid, tokenPair)
    }

    companion object {
        const val CLAIM = "userId"
        const val TOKEN_ACCESS_DAYS = 7.toLong()
    }

}

data class TokenConfig(
    val domain: String,
    val audience: String,
    val realm: String,
    val secret: String
)