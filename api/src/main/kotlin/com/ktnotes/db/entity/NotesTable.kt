package com.ktnotes.db.entity

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.time.ZoneOffset


object NotesTable : UUIDTable("Notes") {
    var title = varchar("note_title", length = 30)
    var note = text("note_text")
    var created = datetime("created").clientDefault { LocalDateTime.now(ZoneOffset.UTC) }
    var isPinned = bool("is_pinned").default(false)
    var updated = datetime("updated").clientDefault { LocalDateTime.now(ZoneOffset.UTC) }
    val user = reference("userId", UserTable)
}