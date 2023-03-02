package com.ktnotes.feature.auth

import com.ktnotes.exceptions.TransactionExceptions
import com.ktnotes.feature.auth.model.AuthResponse
import com.ktnotes.security.token.JWTServiceImp
import com.ktnotes.security.token.TokenClaim
import com.ktnotes.security.token.TokenService

class AuthController(private val userDao: UserDao, private val tokenService: TokenService) {

    fun register(authRequest: AuthRequest): AuthResponse {

        val user = userDao.insertUser(authRequest) ?: throw TransactionExceptions("Problem inserting new user")

        val token = tokenService.generateToken(TokenClaim(JWTServiceImp.CLAIM, user.id))

        return AuthResponse(token)
    }
}