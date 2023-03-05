package com.ktnotes.feature.notes

import com.ktnotes.exceptions.BadRequestException
import com.ktnotes.feature.notes.model.NoteResponse
import com.ktnotes.feature.notes.model.NotesResponse
import com.ktnotes.feature.notes.request.NoteRequest
import com.ktnotes.model.ok
import com.ktnotes.security.getUserIdFromPrinciple
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
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

        val userId = call.getUserIdFromPrinciple()
        val note = notesController.getAllNotes(userId)

        call.ok(NotesResponse(note))
    }

    route("/note") {
        post("/new") {

            val userId = call.getUserIdFromPrinciple()

            val noteRequest = runCatching { call.receive<NoteRequest>() }.getOrElse {
                throw BadRequestException("JSON malformed")
            }
            val note = notesController.insert(noteRequest, userId)

            call.ok(NoteResponse(note))
        }

        get("/{id}") {
            val userId = call.getUserIdFromPrinciple()
            val noteId = call.parameters["id"] ?: return@get
            val response = notesController.getNote(noteId, userId)
            call.ok(response)
        }
    }

}