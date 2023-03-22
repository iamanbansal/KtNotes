package com.ktnotes.feature.note.model

data class Note(
    val id: String,
    val title: String,
    val note: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isPinned: Boolean
)

