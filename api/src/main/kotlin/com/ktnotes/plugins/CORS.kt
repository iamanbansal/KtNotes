package com.ktnotes.plugins

import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS


fun Application.configureCORS() {
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowCredentials = true
        maxAgeInSeconds = 360
    }
}