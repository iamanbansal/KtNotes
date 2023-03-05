package com.ktnotes.feature.notes

import com.ktnotes.exceptions.NoteNotFoundException
import com.ktnotes.exceptions.TransactionExceptions
import com.ktnotes.feature.notes.model.Note
import com.ktnotes.feature.notes.model.NoteResponse
import com.ktnotes.feature.notes.request.NoteRequest
import com.ktnotes.model.Response

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

    fun getNote(noteId: String, userId: String): Response {
        val note = notesDao.getNote(noteId, userId)?: throw NoteNotFoundException("Note $noteId not found")
        return NoteResponse(note)
    }

    fun update(noteRequest: NoteRequest, userid: String, noteId: String) {
        //valid data

        //insert in db
        notesDao.update(noteRequest, userid, noteId) ?: throw TransactionExceptions("Failed to insert note")

    }
}