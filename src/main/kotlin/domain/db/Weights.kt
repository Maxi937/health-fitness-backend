package com.greenmen.domain.db

import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Weights : Table() {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id")
    val weight = decimal("weight", 5, 2)
    val timestamp = timestamp("created").clientDefault { Clock.System.now() }

    override val primaryKey = PrimaryKey(id, name = "PK_Weight_Id")

    init {
        foreignKey(
            Pair(userId, Users.id), name = "fk_user"
        )
    }
}