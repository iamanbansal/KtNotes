package com.ktnotes.feature.notes

import com.ktnotes.db.entity.NotesTable
import com.ktnotes.feature.notes.model.Note
import com.ktnotes.feature.notes.request.NoteRequest
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

interface NotesDao {
    fun getNotes(userid: String): List<Note>
    fun insert(noteRequest: NoteRequest, userid: String): Note?
    fun getNote(noteId: String, userId: String): Note?
    fun update(noteRequest: NoteRequest, userId: String, noteId: String)
    fun delete(userId: String, noteId: String)
}

class NotesDaoImp : NotesDao {

    override fun getNotes(userid: String) = transaction {
        NotesTable.select { NotesTable.user eq UUID.fromString(userid) }
            .sortedWith(compareBy({ NotesTable.isPinned }, { NotesTable.updated }))
            .reversed()
            .map { Note.fromResultRow(it) }
    }

    override fun insert(noteRequest: NoteRequest, userid: String) = transaction {
        NotesTable.insert {
            it[user] = UUID.fromString(userid)
            it[title] = noteRequest.title.trim()
            it[note] = noteRequest.note.trim()
        }.resultedValues?.firstOrNull()?.let { Note.fromResultRow(it) }

    }

    override fun getNote(noteId: String, userId: String) = transaction {
        NotesTable.select {
            (NotesTable.user eq UUID.fromString(userId)) and (NotesTable.id eq UUID.fromString(noteId))
        }.firstOrNull()?.let { Note.fromResultRow(it) }
    }

    override fun update(noteRequest: NoteRequest, userId: String, noteId: String) {
        transaction {
            NotesTable.update({
                (NotesTable.user eq UUID.fromString(userId)) and (NotesTable.id eq UUID.fromString(noteId))
            }) {
                it[title] = noteRequest.title.trim()
                it[note] = noteRequest.note.trim()
            }
        }
    }

    override fun delete(userId: String, noteId: String) {
        transaction {
            NotesTable.deleteWhere {
                (NotesTable.user eq UUID.fromString(userId)) and (NotesTable.id eq UUID.fromString(noteId))
            }
        }
    }
}