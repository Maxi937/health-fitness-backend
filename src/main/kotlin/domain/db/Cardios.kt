package com.greenmen.domain.db

import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Cardios : Table() {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id")
    val distance = decimal("distance", 5, 2)
    val time = long("timeTaken")
    val timestamp = timestamp("created").clientDefault { Clock.System.now() }

    override val primaryKey = PrimaryKey(id, name = "PK_Cardio_Id")

    init {
        foreignKey(
            Pair(userId, Users.id), name = "fk_user"
        )
    }
}