package com.ktnotes.feature.notes.model

import com.ktnotes.db.entity.NotesTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import java.time.LocalDateTime
import java.time.ZoneOffset

@Serializable
data class Note(
    val id: String,
    val title: String,
    val note: String,
    val created: Long,
    val isPinned: Boolean,
    val updated: Long
) {
    companion object {
        fun fromResultRow(row: ResultRow): Note = with(NotesTable) {
            LocalDateTime.now()
            return Note(
                row[id].value.toString(),
                row[title],
                row[note],
                row[created].toEpochSecond(ZoneOffset.UTC),
                row[isPinned],
                row[updated].toEpochSecond(ZoneOffset.UTC)

            )
        }
    }
}