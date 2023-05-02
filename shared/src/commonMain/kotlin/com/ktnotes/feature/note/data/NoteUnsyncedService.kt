package com.ktnotes.feature.note.data

import com.ktnotes.database.NoteQueue
import com.ktnotes.feature.note.db.NoteQueueDao
import com.ktnotes.feature.note.db.NotesDao
import com.ktnotes.feature.note.db.Operations
import com.ktnotes.feature.note.model.Note

interface NoteUnsyncedService {
    suspend fun executeNoteOp(note: Note, operations: Operations)
    suspend fun deleteNoteOp(note: Note)
    suspend fun getAllUnsyncedOp(): List<NoteQueue>
    suspend fun clearAllOps()
}

class NoteUnsyncedServiceImpl(
    private val localRepo: NotesDao,
    private val noteQueueDao: NoteQueueDao
) : NoteUnsyncedService {
    override suspend fun getAllUnsyncedOp(): List<NoteQueue> {
        return noteQueueDao.getUnsyncedNotesOp()
    }

    override suspend fun executeNoteOp(note: Note, operations: Operations) {
        when (operations) {
            Operations.INSERT -> {}
            Operations.UPDATE -> localRepo.updateNote(note)
            Operations.DELETE -> localRepo.deleteNoteById(note.id)
        }
        noteQueueDao.insertOperation(note.id, operations)
    }

    override suspend fun deleteNoteOp(note: Note) {
        noteQueueDao.deleteNoteOp(note.id)
    }

    override suspend fun clearAllOps() {
        noteQueueDao.clearAll()
    }
}