package com.ktnotes.security

import com.ktnotes.feature.auth.model.User
import io.ktor.server.auth.Principal

class UserPrincipal(val user: User) : Principal
