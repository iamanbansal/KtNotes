package com.ktnotes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond


interface Response


suspend fun ApplicationCall.ok(response: Response) {
    respond(HttpStatusCode.OK, response)
}

suspend fun ApplicationCall.notFound(response: Response) {
    respond(HttpStatusCode.NotFound, response)
}

suspend fun ApplicationCall.badRequest(response: Response) {
    respond(HttpStatusCode.BadRequest, response)
}

suspend fun ApplicationCall.unauthorized(response: Response) {
    respond(HttpStatusCode.Unauthorized, response)
}
