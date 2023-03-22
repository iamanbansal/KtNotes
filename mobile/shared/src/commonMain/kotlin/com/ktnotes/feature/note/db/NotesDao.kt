package com.ktnotes.feature.note.db

import com.ktnotes.database.KtNotesDatabase
import com.ktnotes.feature.note.mapper
import com.ktnotes.feature.note.model.Note
import com.ktnotes.feature.note.toNote
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

interface NotesDao {
    suspend fun insertNote(note: Note)
    suspend fun getNoteById(id: String): Note?
    suspend fun getAllNotes(): Flow<List<Note>>
    suspend fun deleteNoteById(id: String)
}


class NotesDaoImpl(notesDatabase: KtNotesDatabase) : NotesDao {
    private val queries = notesDatabase.noteQueries

    override suspend fun insertNote(note: Note) {
        queries.inserNote(
            id = note.id,
            title = note.title,
            note = note.note,
            createdAt = note.createdAt,
            updatedAt = note.updatedAt,
            isPinned = note.isPinned
        )
    }

    override suspend fun getNoteById(id: String,): Note? {
        return queries.getNoteById(id, mapper).executeAsOneOrNull()
    }

    override suspend fun getAllNotes(): Flow<List<Note>> {
        return queries.getAllNotes(mapper).asFlow().mapToList()
    }

    override suspend fun deleteNoteById(id: String) {
        queries.deleteNoteById(id)
    }
}

