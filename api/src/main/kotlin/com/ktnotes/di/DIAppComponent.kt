package com.ktnotes.di

import com.ktnotes.di.components.ApplicationComponent
import com.ktnotes.di.components.ApplicationComponentImpl
import io.ktor.server.application.Application

public class DIAppComponent private constructor() {

    companion object {
        fun builder(): ApplicationComponent.Builder {
            return Builder()
        }
    }

    private class Builder : ApplicationComponent.Builder {
        private lateinit var application: Application

        override fun withApplication(application: Application): ApplicationComponent.Builder {
            this.application = application
            return this
        }

        override fun build(): ApplicationComponent {
            return ApplicationComponentImpl(application)
        }
    }
}

