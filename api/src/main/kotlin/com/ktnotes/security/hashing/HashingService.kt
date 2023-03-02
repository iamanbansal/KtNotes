package com.ktnotes.security.hashing

interface HashingService {
    fun generateSaltedHash(value: String): SaltedHash
    fun verify(value: String, saltedHash: SaltedHash): Boolean
}