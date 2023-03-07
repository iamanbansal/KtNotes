package com.ktnotes.model

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import io.ktor.server.response.respondText


interface Response


suspend fun ApplicationCall.ok(response: Response) {
    respond(HttpStatusCode.OK, response)
}

suspend fun ApplicationCall.notFound(cause: Throwable) {
    respondText(status = HttpStatusCode.NotFound, text = cause.message!!)
}

suspend fun ApplicationCall.badRequest(cause: Throwable) {
    respondText(status = HttpStatusCode.BadRequest, text = cause.message!!)
}

suspend fun ApplicationCall.unauthorized(cause: Throwable) {
    respondText(status = HttpStatusCode.Unauthorized, text = cause.message!!)
}
