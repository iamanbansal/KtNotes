package com.ktnotes.plugins

import com.ktnotes.di.components.ApplicationComponent
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.jwt

fun Application.configureSecurity(appComponent: ApplicationComponent) {
    val tokenService = appComponent.getAuthComponent().getTokenService()

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