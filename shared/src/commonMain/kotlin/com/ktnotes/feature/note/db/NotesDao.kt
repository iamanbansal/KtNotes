package com.ktnotes.feature.note.db

import com.ktnotes.database.KtNotesDatabase
import com.ktnotes.feature.note.mapper
import com.ktnotes.feature.note.model.Note
import com.ktnotes.feature.note.model.NoteWithOperation
import com.ktnotes.feature.note.noteOpMapper
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

interface NotesDao {
    suspend fun insertNote(note: Note)
    suspend fun insertNotes(notes: List<Note>)
    suspend fun getNoteById(id: String): Note?
    suspend fun getAllNotes(): Flow<List<Note>>
    suspend fun deleteNoteById(id: String)
    suspend fun deleteNotIn(noteIds: List<String>)
    suspend fun updateNote(note: Note)
    suspend fun getUnsyncedNotes(): List<NoteWithOperation>
    suspend fun clearAll()
    suspend fun insertNotesAndDeleteNotIn(newNotes: List<Note>)
}


class NotesDaoImpl(notesDatabase: KtNotesDatabase) : NotesDao {
    private val queries = notesDatabase.noteQueries

    override suspend fun insertNote(note: Note) {
        insert(note)
    }

    override suspend fun insertNotes(notes: List<Note>) {
        queries.transaction {
            //todo use afterRollback{} in case error
            notes.forEach { insert(it) }
        }
    }

    private fun insert(note: Note) {
        queries.inserOrReplaceNote(
            id = note.id,
            title = note.title,
            note = note.note,
            createdAt = note.created,
            updatedAt = note.updated,
            isPinned = note.isPinned
        )
    }

    override suspend fun getUnsyncedNotes(): List<NoteWithOperation> {
        return queries.getUnsyncedNotes(noteOpMapper).executeAsList()
    }

    override suspend fun getNoteById(id: String): Note? {
        return queries.getNoteById(id, mapper).executeAsOneOrNull()
    }

    override suspend fun getAllNotes(): Flow<List<Note>> {
        return queries.getAllNotes(mapper).asFlow().mapToList()
    }

    override suspend fun deleteNoteById(id: String) {
        queries.deleteNoteById(id)
    }

    override suspend fun updateNote(note: Note) {
        queries.inserOrReplaceNote(
            id = note.id,
            title = note.title,
            note = note.note,
            createdAt = note.created,
            updatedAt = note.updated,
            isPinned = note.isPinned
        )
    }

    override suspend fun insertNotesAndDeleteNotIn(newNotes: List<Note>) {
        queries.transaction {
            queries.deleteNotesNotIn(newNotes.map { it.id })
            newNotes.forEach {
                insert(it)
            }
        }
    }

    override suspend fun deleteNotIn(noteIds: List<String>) {
        queries.deleteNotesNotIn(noteIds)
    }

    override suspend fun clearAll() {
        queries.clearAllNotes()
    }
}

