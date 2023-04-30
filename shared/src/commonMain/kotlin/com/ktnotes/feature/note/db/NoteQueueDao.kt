package com.ktnotes.feature.note.db

import com.ktnotes.database.KtNotesDatabase
import com.ktnotes.database.NoteQueue

enum class Operations(val opNum: Int) {
    INSERT(1),
    DELETE(2),
    UPDATE(3)
}

interface NoteQueueDao {
    suspend fun insertOperation(noteId: String, op: Operations)
    suspend fun insertNotes(notes: List<NoteQueue>)
    suspend fun getUnsyncedNotesOp(): List<NoteQueue>
    suspend fun deleteNoteOp(noteId: String)
    suspend fun clearAll()
}


class NoteQueueDaoImpl(notesDatabase: KtNotesDatabase) : NoteQueueDao {
    private val queries = notesDatabase.noteQueueQueries
    override suspend fun insertOperation(noteId: String, op: Operations) {
        queries.insertOrReplaceNote(
            noteId,
            op.opNum.toLong()
        )
    }

    override suspend fun insertNotes(notes: List<NoteQueue>) {
        //skip
    }

    override suspend fun getUnsyncedNotesOp():List<NoteQueue> {
        return queries.getUnsyncedNotesOp().executeAsList()
    }

    override suspend fun deleteNoteOp(noteId: String) {
        queries.deleteNoteOpById(noteId)
    }

    override suspend fun clearAll() {
        queries.clearUnsyncedRows()
    }
}

