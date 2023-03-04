package com.ktnotes.feature.notes

import com.ktnotes.exceptions.TransactionExceptions
import com.ktnotes.feature.notes.model.Note
import com.ktnotes.feature.notes.request.NoteRequest

class NotesController(
    private val notesDao: NotesDao,
) {
    fun insert(noteRequest: NoteRequest, userid: String): Note {
        //valid data

        //insert in db

        return notesDao.insert(noteRequest, userid) ?: throw TransactionExceptions("Failed to insert note")

    }

    fun getAllNotes(userId: String): List<Note> {
        return notesDao.getNotes(userId)
    }
}