package com.ktnotes.plugins

import com.ktnotes.exceptions.BadRequestException
import com.ktnotes.exceptions.NoteNotFoundException
import com.ktnotes.exceptions.UnauthorizedActivityException
import com.ktnotes.model.badRequest
import com.ktnotes.model.notFound
import com.ktnotes.model.unauthorized
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is BadRequestException -> call.badRequest(cause)
                is UnauthorizedActivityException -> call.unauthorized(cause)
                is NoteNotFoundException -> call.notFound(cause)
            }
        }
    }
}