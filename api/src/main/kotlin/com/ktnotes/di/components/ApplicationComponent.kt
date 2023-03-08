package com.ktnotes.di.components

import io.ktor.server.application.Application

interface ApplicationComponent {
    val application: Application

    fun getAuthComponent(): AuthComponent
    fun getDatabaseComponent(): DatabaseComponent
    fun getNoteComponent(): NoteComponent

    interface Builder {
        fun withApplication(application: Application): Builder
        fun build(): ApplicationComponent
    }
}

class ApplicationComponentImpl(override val application: Application) : ApplicationComponent {
    private var authComponent: AuthComponent = AuthComponentImpl(this)
    private var databaseComponent: DatabaseComponent = DatabaseComponentImpl(this)
    private var noteComponent: NoteComponent = NoteComponentImpl(this)

    override fun getAuthComponent(): AuthComponent {
        return authComponent
    }

    override fun getDatabaseComponent(): DatabaseComponent {
        return databaseComponent
    }

    override fun getNoteComponent(): NoteComponent {
        return noteComponent
    }
}

