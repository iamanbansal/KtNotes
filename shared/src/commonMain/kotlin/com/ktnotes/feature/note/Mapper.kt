package com.ktnotes.feature.note

import com.ktnotes.database.NoteEntity
import com.ktnotes.feature.note.model.Note
import com.ktnotes.feature.note.model.NoteWithOperation

fun NoteEntity.toNote(): Note {
    return Note(id, title, note, createdAt, updatedAt, isPinned)
}

val mapper: (
    id: String,
    title: String,
    note: String,
    createdAt: Long,
    updatedAt: Long,
    isPinned: Boolean
) -> Note = { id, title, note, createdAt, updatedAt, isPinned ->
    Note(id, title, note, createdAt, updatedAt, isPinned)
}

val noteOpMapper: (
    id: String,
    title: String,
    note: String,
    createdAt: Long,
    updatedAt: Long,
    isPinned: Boolean,
    operation:Long
) -> NoteWithOperation = { id, title, note, createdAt, updatedAt, isPinned, operation ->
    NoteWithOperation(Note(id, title, note, createdAt, updatedAt, isPinned), operation.toInt())
}