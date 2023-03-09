package com.ktnotes.feature.notes

import com.ktnotes.di.components.ApplicationComponent
import com.ktnotes.exceptions.BadRequestException
import com.ktnotes.feature.notes.model.NoteResponse
import com.ktnotes.feature.notes.model.NotesResponse
import com.ktnotes.feature.notes.request.NoteRequest
import com.ktnotes.model.MessageResponse
import com.ktnotes.model.ok
import com.ktnotes.security.getUserIdFromPrinciple
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing


fun Application.authenticatedNotesRouting(appComponent: ApplicationComponent) {
    routing {
        authenticate {
            notesRouting(appComponent)
        }
    }
}

fun Route.notesRouting(appComponent: ApplicationComponent) {

    val notesController by appComponent.getNoteComponent().getNotesController()

    get("/notes") {

        val userId = call.getUserIdFromPrinciple()
        val note = notesController.getAllNotes(userId)

        call.ok(NotesResponse(note))
    }

    get("/search") {

        val userId = call.getUserIdFromPrinciple()
        val query = call.parameters["q"] ?: return@get

        val notes = notesController.getNotesByQuery(userId, query)

        call.ok(notes)
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

        put("/{id}") {

            val userId = call.getUserIdFromPrinciple()
            val noteId = call.parameters["id"] ?: return@put

            val noteRequest = runCatching { call.receive<NoteRequest>() }.getOrElse {
                throw BadRequestException("JSON malformed")
            }

            notesController.update(noteRequest, userId, noteId)

            call.ok(MessageResponse("Note updated successfully!"))
        }

        delete("/{id}") {

            val userId = call.getUserIdFromPrinciple()
            val noteId = call.parameters["id"] ?: return@delete

            notesController.delete(userId, noteId)

            call.ok(MessageResponse("Note deleted successfully!"))
        }
    }

}