package com.ktnotes.feature.auth

import com.ktnotes.exceptions.BadRequestException
import com.ktnotes.exceptions.TransactionExceptions
import com.ktnotes.exceptions.UnauthorizedActivityException
import com.ktnotes.feature.auth.model.AuthResponse
import com.ktnotes.feature.auth.request.AuthRequest
import com.ktnotes.model.Response
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

    fun register(authRequest: AuthRequest): Response {

        validateRequest(authRequest)

        if (userDao.isEmailExist(authRequest.email)) {
            throw BadRequestException("User already exist")
        }

        val saltedHash = hashingService.generateSaltedHash(authRequest.password)

        val user = userDao.insertUser(authRequest, saltedHash)
            ?: throw TransactionExceptions("Problem inserting new user")

        val tokenPair = tokenService.generateToken(TokenClaim(JWTServiceImp.CLAIM, user.id))

        tokenService.insertToken(user.id, tokenPair)

        return AuthResponse(tokenPair.accessToken, tokenPair.refreshToken)
    }

    fun login(loginRequest: AuthRequest): Response {

        val user = userDao.getUserByEmail(loginRequest.email)
            ?: throw BadRequestException("Invalid Credentials")

        val isPasswordValid = hashingService.verify(loginRequest.password, SaltedHash(user.salt, user.password))

        if (!isPasswordValid) {
            throw BadRequestException("Invalid Credentials")
        }

        val tokenPair = tokenService.generateToken(TokenClaim(JWTServiceImp.CLAIM, user.id))
        tokenService.updateToken(user.id, tokenPair)
        return AuthResponse(tokenPair.accessToken, tokenPair.refreshToken)
    }

    fun refreshToken(refreshToken: String): Response {
        val pair = tokenService.validateRefreshToken(refreshToken)
        return AuthResponse(pair.accessToken, pair.refreshToken)

    }


    private fun validateRequest(authRequest: AuthRequest) {
        val message = when {
            authRequest.email.isValidEmail() -> "Invalid Email address"
            authRequest.name.isNullOrBlank() -> "Name can not be blank"
            authRequest.name.length > 50 -> "Name can not be more than 50 characters"
            authRequest.password.length < 8 -> "Password length should be more than 8 characters"
            else -> ""
        }

        if (message.isNotBlank()) {
            throw BadRequestException(message)
        }
    }

}