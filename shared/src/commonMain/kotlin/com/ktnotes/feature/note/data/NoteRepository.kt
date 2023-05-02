package com.ktnotes.feature.note.data

import com.ktnotes.feature.note.db.NotesDao
import com.ktnotes.feature.note.model.Note
import com.ktnotes.feature.note.remote.NoteRequest
import com.ktnotes.feature.note.remote.NotesApi
import com.ktnotes.feature.note.remote.PinRequest
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getNotesFromRemote()
    suspend fun getNotesListFromRemote():List<Note>
    suspend fun insertNote(title: String, note: String)
    suspend fun updateNote(note: Note)
    suspend fun getNoteById(id: String): Note?
    suspend fun getAllNotes(): Flow<List<Note>>
    suspend fun deleteNoteById(id: String)
    suspend fun pinNote(id: String, isPinned: Boolean)
    suspend fun clearAll()
}

class NoteRepositoryImpl(
    private val notesApi: NotesApi,
    private val notesDao: NotesDao
) : NoteRepository {

    override suspend fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

    override suspend fun getNotesListFromRemote(): List<Note> {
        return notesApi.getAllNotes().result
    }
    override suspend fun getNotesFromRemote() {
        //todo use worker to sync notes
        val notes = notesApi.getAllNotes().result
        notesDao.insertNotes(notes)
    }

    override suspend fun insertNote(title: String, note: String) {
        val noteResponse = notesApi.saveNote(NoteRequest(title, note))
        notesDao.insertNote(noteResponse.result)
    }

    override suspend fun updateNote(note: Note) {
        val noteResponse = notesApi.updateNote(note)
        notesDao.updateNote(noteResponse.result)
    }

    override suspend fun getNoteById(id: String): Note? {
        return notesDao.getNoteById(id)
    }

    override suspend fun deleteNoteById(id: String) {
        notesApi.deleteNote(id)
        notesDao.deleteNoteById(id)
    }

    override suspend fun pinNote(id: String, isPinned: Boolean) {
        val noteResponse = notesApi.pinNote(id, PinRequest(isPinned))
        notesDao.updateNote(noteResponse.result)
    }

    override suspend fun clearAll() {
        notesDao.clearAll()
    }
}
