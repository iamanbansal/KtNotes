package com.ktnotes.feature.note.model

import kotlinx.serialization.Serializable


@Serializable
data class Note(
    val id: String,
    val title: String,
    val note: String,
    val created: Long,
    val updated: Long,
    val isPinned: Boolean
)

