package com.greenmen.domain.db

import org.jetbrains.exposed.sql.Table

object Users : Table("users") {
    val id = integer(name = "id").autoIncrement()
    val name = varchar("name", 100)
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)

    override val primaryKey = PrimaryKey(id, name = "PK_Users_Id")
}