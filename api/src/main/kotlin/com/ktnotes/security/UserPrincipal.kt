package com.ktnotes.security

import com.ktnotes.exceptions.UnauthorizedActivityException
import com.ktnotes.feature.auth.model.User
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.Principal
import io.ktor.server.auth.principal

class UserPrincipal(val user: User) : Principal


fun ApplicationCall.getUserIdFromPrinciple(): String {
    val principal = principal<UserPrincipal>()
        ?: throw UnauthorizedActivityException("Unauthenticated Access")
    return principal.user.id
}

