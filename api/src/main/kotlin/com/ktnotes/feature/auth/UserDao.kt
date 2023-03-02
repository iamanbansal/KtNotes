package com.ktnotes.feature.auth

import com.ktnotes.entity.UserTable
import com.ktnotes.feature.auth.model.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

interface UserDao {
    fun insertUser(authRequest: AuthRequest): User?
    fun findByUUID(id: UUID): User?
}

class UserDaoImpl : UserDao {
    override fun insertUser(authRequest: AuthRequest): User? = transaction {
        return@transaction UserTable.insert {
            it[name] = authRequest.name
            it[email] = authRequest.email
            it[password] = authRequest.password
            it[salt] = authRequest.password
        }.resultedValues?.firstOrNull()?.let { User.fromResultRow(it) }
    }

    override fun findByUUID(id: UUID): User? = transaction {
        return@transaction UserTable.select { UserTable.id eq id }
            .firstOrNull()?.let {
                User.fromResultRow(it)
            }
    }

}