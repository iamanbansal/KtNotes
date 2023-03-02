package com.ktnotes.plugins

import com.ktnotes.feature.auth.AuthController
import com.ktnotes.feature.auth.authRouting
import io.ktor.server.application.Application

fun Application.configureRouting(authController: AuthController) {
    authRouting(authController)
}
