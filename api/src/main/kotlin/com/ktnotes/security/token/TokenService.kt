package com.ktnotes.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.ktnotes.feature.auth.UserDao
import com.ktnotes.security.UserPrincipal
import io.ktor.server.auth.Principal
import io.ktor.server.auth.jwt.JWTCredential
import java.util.*

interface TokenService {
    fun generateToken(vararg claims: TokenClaim): String
    fun getVerifier(): JWTVerifier
    fun validate(credential: JWTCredential): Principal?
}

class JWTServiceImp(private val config: TokenConfig, private val userDao: UserDao) : TokenService {


    override fun generateToken(vararg claims: TokenClaim): String {
        val builder = JWT.create()
            .withIssuer(config.domain)
            .withAudience(config.audience)
            .withExpiresAt(Date(System.currentTimeMillis() + 7*24*60*60*1000))

        claims.forEach {
            builder.withClaim(it.name, it.value)
        }

        return builder.sign(Algorithm.HMAC256(config.secret))
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

    companion object {
        const val CLAIM = "userId"
    }

}

data class TokenConfig(
    val domain: String,
    val audience: String,
    val realm: String,
    val secret: String
)