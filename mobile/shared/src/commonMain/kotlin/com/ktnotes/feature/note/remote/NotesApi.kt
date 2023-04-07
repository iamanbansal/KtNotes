package com.ktnotes.feature.note.remote

import com.ktnotes.feature.note.model.Note
import com.ktnotes.helper.postWithJsonContent
import com.ktnotes.helper.putWithJsonContent
import com.ktnotes.model.MessageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.setBody

interface NotesApi {
    suspend fun saveNote(noteRequest: NoteRequest): NoteResponse
    suspend fun getAllNotes(): NotesResponse
    suspend fun deleteNote(id: String): MessageResponse
    suspend fun updateNote(note: Note): NoteResponse
    suspend fun pinNote(id: String, pinRequest: PinRequest): NoteResponse
}


class NotesApiImpl(private val httpClient: HttpClient) : NotesApi {
    override suspend fun saveNote(noteRequest: NoteRequest): NoteResponse {
        return httpClient.postWithJsonContent("note/new") {
            setBody(noteRequest)
        }.body()
    }

    override suspend fun getAllNotes(): NotesResponse {
        return httpClient.get("notes").body()
    }

    override suspend fun deleteNote(id: String): MessageResponse {
        return httpClient.delete("note/$id").body()
    }

    override suspend fun updateNote(note: Note): NoteResponse {
        return httpClient.putWithJsonContent("note/${note.id}") {
            setBody(note)
        }.body()
    }

    override suspend fun pinNote(id: String, pinRequest: PinRequest): NoteResponse {
        return httpClient.putWithJsonContent("note/pin/$id") {
            setBody(pinRequest)
        }.body()
    }
}

