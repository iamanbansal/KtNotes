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
    respond(HttpStatusCode.NotFound, MessageResponse(cause.message!!))
}

suspend fun ApplicationCall.badRequest(cause: Throwable) {
    respond(HttpStatusCode.BadRequest, MessageResponse(cause.message!!))
}

suspend fun ApplicationCall.unauthorized(cause: Throwable) {
    respond(HttpStatusCode.Unauthorized, MessageResponse(cause.message!!))
}
