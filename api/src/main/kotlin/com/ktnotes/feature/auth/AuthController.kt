package com.ktnotes.feature.auth

import com.ktnotes.exceptions.BadRequestException
import com.ktnotes.exceptions.TransactionExceptions
import com.ktnotes.exceptions.UnauthorizedActivityException
import com.ktnotes.feature.auth.model.AuthResponse
import com.ktnotes.feature.auth.request.AuthRequest
import com.ktnotes.feature.auth.request.LoginRequest
import com.ktnotes.security.hashing.HashingService
import com.ktnotes.security.hashing.SaltedHash
import com.ktnotes.security.token.JWTServiceImp
import com.ktnotes.security.token.TokenClaim
import com.ktnotes.security.token.TokenService
import com.ktnotes.util.isValidEmail

class AuthController(
    private val userDao: UserDao,
    private val tokenService: TokenService,
    private val hashingService: HashingService
) {

    fun register(authRequest: AuthRequest): AuthResponse {

        validateRequest(authRequest)

        if (userDao.isEmailExist(authRequest.email)) {
            throw BadRequestException("User already exist")
        }

        val saltedHash = hashingService.generateSaltedHash(authRequest.password)

        val user = userDao.insertUser(authRequest, saltedHash)
            ?: throw TransactionExceptions("Problem inserting new user")

        val token = tokenService.generateToken(TokenClaim(JWTServiceImp.CLAIM, user.id))

        return AuthResponse(token)
    }

    fun login(loginRequest: LoginRequest): AuthResponse {

        val user = userDao.getUserByEmail(loginRequest.email)
            ?: throw UnauthorizedActivityException("Invalid Credentials")

        val isPasswordValid = hashingService.verify(loginRequest.password, SaltedHash(user.salt, user.password))

        if (!isPasswordValid) {
            throw UnauthorizedActivityException("Invalid Credentials")
        }

        val token = tokenService.generateToken(TokenClaim(JWTServiceImp.CLAIM, user.id))

        return AuthResponse(token)
    }


    private fun validateRequest(authRequest: AuthRequest) {
        val message = when {
            authRequest.email.isValidEmail() -> "Invalid Email address"
            authRequest.name.isBlank() -> "Name can not be blank"
            authRequest.name.length > 50 -> "Name can not be more than 50 characters"
            authRequest.password.length < 8 -> "Password length should be more than 8 characters"
            else -> ""
        }

        if (message.isNotBlank()) {
            throw BadRequestException(message)
        }
    }

}