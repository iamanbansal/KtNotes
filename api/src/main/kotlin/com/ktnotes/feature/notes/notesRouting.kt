package com.ktnotes.feature.notes

import com.ktnotes.exceptions.BadRequestException
import com.ktnotes.exceptions.UnauthorizedActivityException
import com.ktnotes.feature.notes.model.NoteResponse
import com.ktnotes.feature.notes.model.NotesResponse
import com.ktnotes.feature.notes.request.NoteRequest
import com.ktnotes.model.ok
import com.ktnotes.security.UserPrincipal
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing


fun Application.authenticatedNotesRouting(notesController: NotesController) {
    routing {
        authenticate {
            notesRouting(notesController)
        }
    }
}

fun Route.notesRouting(notesController: NotesController) {

    get("/notes") {
        val principal = call.principal<UserPrincipal>()
            ?: throw UnauthorizedActivityException("Unauthenticated Access")

        val userId = principal.user.id
        val note = notesController.getAllNotes(userId)

        call.ok(NotesResponse(note))
    }

    route("/note") {
        post("/new") {

            val principal = call.principal<UserPrincipal>()
                ?: throw UnauthorizedActivityException("Unauthenticated Access")

            val noteRequest = runCatching { call.receive<NoteRequest>() }.getOrElse {
                throw BadRequestException("JSON malformed")
            }

            val userId = principal.user.id
            val note = notesController.insert(noteRequest, userId)

            call.ok(NoteResponse(note))
        }
    }

}


