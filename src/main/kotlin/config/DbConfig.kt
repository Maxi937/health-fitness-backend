package com.greenmen.config

import com.greenmen.domain.db.Cardios
import com.greenmen.domain.db.Users
import com.greenmen.domain.db.Weights
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DbConfig(
    val host: String,
    val port: String,
    val name: String,
    val driver: String,
    val username: String,
    val password: String,
) {
    fun getDbConnection(): Database {
        try {
            println("Connecting to:\njdbc:postgresql://$host:$port/$name")
            val db = Database.connect(
                url = "jdbc:postgresql://$host:$port/$name",
                driver = driver,
                user = username,
                password = password,
            )

            transaction {
                SchemaUtils.create(Users)
                SchemaUtils.create(Weights)
                SchemaUtils.create(Cardios)
            }

            return db
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("could not connect to db")
        }
    }
}