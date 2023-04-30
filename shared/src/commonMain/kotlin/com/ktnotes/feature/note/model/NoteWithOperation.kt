package com.ktnotes.feature.note.model

import kotlinx.serialization.Serializable


@Serializable
data class NoteWithOperation(val note: Note, val operation:Int)
