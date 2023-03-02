package com.ktnotes.security.hashing


import org.mindrot.jbcrypt.BCrypt

class BCryptHashingService : HashingService {
    override fun generateSaltedHash(value: String): SaltedHash {

        val salt = BCrypt.gensalt()
        val hash = BCrypt.hashpw(value, salt)
        return SaltedHash(salt, hash)
    }

    override fun verify(value: String, saltedHash: SaltedHash): Boolean {
        return BCrypt.hashpw(value, saltedHash.salt) == saltedHash.hash
    }
}