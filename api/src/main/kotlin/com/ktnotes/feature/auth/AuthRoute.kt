package com.ktnotes.feature.auth

import com.ktnotes.di.components.ApplicationComponent
import com.ktnotes.exceptions.BadRequestException
import com.ktnotes.feature.auth.request.AuthRequest
import com.ktnotes.feature.auth.request.RefreshTokenRequest
import com.ktnotes.model.ok
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing


fun Application.authRouting(appComponent: ApplicationComponent) {
    val authController by appComponent.getAuthComponent().getAuthController()

    routing {
        route("/auth") {

            post("/register") {
                val authRequest = runCatching { call.receive<AuthRequest>() }.getOrElse {
                    throw BadRequestException("Malformed JSON Body")
                }

                val authResponse = authController.register(authRequest)

                call.ok(authResponse)
            }

            post("/login") {
                val authRequest = runCatching { call.receive<AuthRequest>() }.getOrElse {
                    throw BadRequestException("Malformed JSON Body")
                }

                val authResponse = authController.login(authRequest)

                call.ok(authResponse)
            }

            post("/refresh") {
                val oldRefreshToken = call.receive<RefreshTokenRequest>().refreshToken

                val authResponse = authController.refreshToken(oldRefreshToken)

                call.ok(authResponse)
            }

        }
    }
}

