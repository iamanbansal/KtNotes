package com.ktnotes.plugins

import com.ktnotes.di.components.ApplicationComponent
import com.ktnotes.feature.auth.authRouting
import com.ktnotes.feature.notes.authenticatedNotesRouting
import io.ktor.server.application.Application

fun Application.configureRouting(appComponent: ApplicationComponent) {
    authRouting(appComponent)
    authenticatedNotesRouting(appComponent)
}
