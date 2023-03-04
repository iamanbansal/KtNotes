package com.ktnotes.db.entity

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate


object NotesTable : UUIDTable("Notes") {
    var title = varchar("note_title", length = 30)
    var note = text("note_text")
    var created = date("created").default(LocalDate.now())
    var isPinned = bool("is_pinned").default(false)
    var updated = date("updated").default(LocalDate.now())
    val user = reference("userId", UserTable)
}