package com.ktnotes.android.nav

import com.ktnotes.feature.note.details.NEW_NOTE_ID

sealed class Screen(val route: String, val name: String) {
    object Register : Screen("register", "Registration")
    object Notes : Screen("notes", "Notes")
    object NoteDetails : Screen("note/{noteId}", "Note Details") {
        fun route(noteId: String) = "note/$noteId"
        const val ARG_NOTE_ID: String = "noteId"
    }

    object AddNote : Screen("note/$NEW_NOTE_ID", "New note")
}
