package com.ktnotes.plugins

import com.ktnotes.feature.auth.AuthController
import com.ktnotes.feature.auth.authRouting
import com.ktnotes.feature.notes.NotesController
import com.ktnotes.feature.notes.NotesDaoImp
import com.ktnotes.feature.notes.authenticatedNotesRouting
import io.ktor.server.application.Application

fun Application.configureRouting(authController: AuthController, notesController: NotesController) {
    authRouting(authController)
    authenticatedNotesRouting(notesController)
}
