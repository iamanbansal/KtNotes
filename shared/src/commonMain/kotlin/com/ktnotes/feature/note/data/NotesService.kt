package com.ktnotes.feature.note.data

import com.ktnotes.feature.note.db.NotesDao
import com.ktnotes.feature.note.db.Operations
import com.ktnotes.feature.note.model.Note
import com.ktnotes.feature.note.remote.NotesApi
import kotlinx.coroutines.flow.Flow


interface NotesService {
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun syncNotes()
    suspend fun getAllNotes(): Flow<List<Note>>
    suspend fun clearAll()
}

class NotesServiceImpl(
    private val remoteRepository: NotesApi,
    private val noteUnsyncedService: NoteUnsyncedService,
    private val notesDao: NotesDao
) : NotesService {

    override suspend fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

    override suspend fun updateNote(note: Note) {
        runCatching {
            remoteRepository.updateNote(note)
            noteUnsyncedService.deleteNoteOp(note)
        }.onFailure {
            noteUnsyncedService.executeNoteOp(note, Operations.UPDATE)
        }
    }

    override suspend fun deleteNote(note: Note) {
        runCatching {
            remoteRepository.deleteNote(note.id)
            noteUnsyncedService.executeNoteOp(note, Operations.DELETE)
        }.onFailure {
            noteUnsyncedService.executeNoteOp(note, Operations.DELETE)
        }
    }

    override suspend fun syncNotes() {
        val notesWithOp = notesDao.getUnsyncedNotes()

        //TODO: build backend which accept note with operation list to avoid item wise item calls

        runCatching {
            notesWithOp.forEach {
                when (it.noteOperation.operation) {
                    Operations.UPDATE.opNum -> remoteRepository.updateNote(it.note!!)
                    Operations.DELETE.opNum -> remoteRepository.deleteNote(it.noteOperation.noteId)
                }
            }
            noteUnsyncedService.clearAllOps()
        }.onFailure {
            throw Exception("Failed to sync notes")
        }
        synNotesFromServer()
    }

    private suspend fun synNotesFromServer() {
        runCatching {
            remoteRepository.getAllNotes().result
        }.onSuccess {
            saveToDb(it)
        }.onFailure {
            throw Exception("Failed to sync new notes")
        }
    }

    private suspend fun saveToDb(newNotes: List<Note>) {
        notesDao.insertNotesAndDeleteNotIn(newNotes)
    }

    override suspend fun clearAll() {
        notesDao.clearAll()
        noteUnsyncedService.clearAllOps()
    }
}