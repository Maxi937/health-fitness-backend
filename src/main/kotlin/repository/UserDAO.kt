package com.greenmen.repository

import com.greenmen.domain.User
import com.greenmen.domain.db.Users
import com.greenmen.domain.utils.mapToUser
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserDAO {

    fun getAll(): List<User> {
        val users = mutableListOf<User>()
        transaction {
            Users.selectAll().forEach {
                users.add(it.mapToUser())
            }
        }
        return users
    }

    fun save(user: User) {
        Users.insert {
            it[id] = user.id
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password
        }
    }

    fun new(email: String, name: String, password: String): Boolean {
        return try {
            transaction {
                Users.insert {
                    it[Users.name] = name
                    it[Users.email] = email
                    it[Users.password] = password
                }
            }
            true
        } catch (err: Exception) {
            println(err)// Handle the case where the email already exists
            false
        }
    }

    fun findById(id: Int): User? {
        return transaction {
            Users.selectAll().where {
                Users.id eq id
            }.map { it.mapToUser() }.firstOrNull()
        }
    }

    fun findByEmail(email: String): User? {
        return transaction {
            Users.selectAll().where {
                Users.email eq email
            }.map { it.mapToUser() }.firstOrNull()
        }
    }
}