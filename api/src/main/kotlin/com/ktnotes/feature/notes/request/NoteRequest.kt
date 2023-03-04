package com.ktnotes.feature.notes.request

import com.ktnotes.db.entity.NotesTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import java.time.LocalDateTime
import java.time.ZoneOffset

@Serializable
data class NoteRequest(
    val title: String,
    val note: String
)