package com.ktnotes.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.ktnotes.security.token.TokenConfig
import com.ktnotes.security.token.JWTServiceImp
import io.ktor.server.application.*
import io.ktor.server.config.ApplicationConfig

fun Application.configureSecurity(tokenService: JWTServiceImp) {
    authentication {
        jwt {

            verifier(
                tokenService.getVerifier()
            )
            validate { credential ->
                tokenService.validate(credential)
            }
        }
    }
}