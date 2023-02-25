package com.ktnotes.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*

fun Application.configureSecurity() {

    authentication {

        val config = this@configureSecurity.environment.config
        jwt {
            val jwtAudience = config.property("jwt.audience").getString()
            val secret = config.property("key.secret").getString()
            val domain = config.property("jwt.domain").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(jwtAudience)
                    .withIssuer(domain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}
