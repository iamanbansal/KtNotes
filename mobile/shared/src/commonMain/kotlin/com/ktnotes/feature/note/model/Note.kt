package com.ktnotes.feature.note.model

import com.ktnotes.util.DateTimeUtil
import kotlinx.serialization.Serializable


@Serializable
data class Note(
    val id: String,
    val title: String,
    val note: String,
    val created: Long,
    val updated: Long,
    val isPinned: Boolean
) {
    val formattedCreatedDate: String =
        DateTimeUtil.formatNoteDate(DateTimeUtil.toLocalDateTime(created))
}

