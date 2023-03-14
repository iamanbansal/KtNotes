package com.ktnotes.feature.auth

import com.ktnotes.db.entity.UserTable
import com.ktnotes.feature.auth.model.User
import com.ktnotes.feature.auth.request.AuthRequest
import com.ktnotes.security.hashing.SaltedHash
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

interface UserDao {
    fun insertUser(authRequest: AuthRequest, saltedHash: SaltedHash): User?
    fun findByUUID(id: UUID): User?
    fun isEmailExist(email: String): Boolean
    fun getUserByEmail(email: String): User?
}

class UserDaoImpl : UserDao {
    override fun insertUser(authRequest: AuthRequest, saltedHash: SaltedHash): User? = transaction {
        return@transaction UserTable.insert {
            it[name] = authRequest.name!!
            it[email] = authRequest.email
            it[password] = saltedHash.hash
            it[salt] = saltedHash.salt
        }.resultedValues?.firstOrNull()?.let { User.fromResultRow(it) }
    }

    override fun findByUUID(id: UUID): User? = transaction {
        return@transaction UserTable.select { UserTable.id eq id }
            .firstOrNull()?.let {
                User.fromResultRow(it)
            }
    }

    override fun isEmailExist(email: String): Boolean {
        return transaction {
            UserTable.select { UserTable.email eq email }.firstOrNull() != null
        }
    }

    override fun getUserByEmail(email: String): User? {
        return transaction {
            UserTable.select { UserTable.email eq email }.firstOrNull()?.let {
                User.fromResultRow(it)
            }
        }
    }

}