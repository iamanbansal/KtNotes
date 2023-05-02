package com.ktnotes.feature.note.model

import kotlinx.serialization.Serializable


@Serializable
data class NoteWithOperation(val note: Note? = null, val noteOperation: NoteOperation)


@Serializable
data class NoteOperation(val noteId:String, val operation: Int)


