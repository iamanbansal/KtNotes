package com.ktnotes.feature.notes

import com.ktnotes.exceptions.BadRequestException
import com.ktnotes.exceptions.NoteNotFoundException
import com.ktnotes.exceptions.TransactionExceptions
import com.ktnotes.feature.notes.model.Note
import com.ktnotes.feature.notes.model.NoteResponse
import com.ktnotes.feature.notes.model.NotesResponse
import com.ktnotes.feature.notes.request.NoteRequest
import com.ktnotes.model.Response

class NotesController(
    private val notesDao: NotesDao,
) {
    fun insert(noteRequest: NoteRequest, userid: String): Note {
        return notesDao.insert(noteRequest, userid) ?: throw TransactionExceptions("Failed to insert note")
    }

    fun getAllNotes(userId: String): List<Note> {
        return notesDao.getNotes(userId)
    }

    fun getNote(noteId: String, userId: String): Response {
        val note = notesDao.getNote(noteId, userId) ?: throw NoteNotFoundException("Note not found")
        return NoteResponse(note)
    }

    fun update(noteRequest: NoteRequest, userid: String, noteId: String): Response {
        val note = runCatching {
            notesDao.update(noteRequest, userid, noteId)
            notesDao.getNote(noteId, userid)
        }.getOrElse {
            throw BadRequestException("Unable to update note")
        }
        requireNotNull(note) {
            throw NoteNotFoundException("Note not found")
        }
        return NoteResponse(note)
    }

    fun delete(userId: String, noteId: String) {
        runCatching {
            notesDao.delete(userId, noteId)
        }.getOrElse {
            throw BadRequestException("Unable to delete note")
        }
    }

    fun getNotesByQuery(userId: String, query: String): Response {
        val list = notesDao.search(userId, query)
        return NotesResponse(list)
    }
}