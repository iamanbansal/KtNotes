package com.ktnotes.feature.auth

import com.ktnotes.exceptions.BadRequestException
import com.ktnotes.feature.auth.request.AuthRequest
import com.ktnotes.feature.auth.request.LoginRequest
import com.ktnotes.model.ok
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing


fun Application.authRouting(authController: AuthController) {
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
                val loginRequest = runCatching { call.receive<LoginRequest>() }.getOrElse {
                    throw BadRequestException("Malformed JSON Body")
                }

                val authResponse = authController.login(loginRequest)

                call.ok(authResponse)
            }
        }
    }
}

