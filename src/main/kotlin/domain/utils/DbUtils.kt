package com.greenmen.domain.utils

import com.greenmen.domain.Cardio
import com.greenmen.domain.User
import com.greenmen.domain.Weight
import com.greenmen.domain.db.Cardios
import com.greenmen.domain.db.Users
import com.greenmen.domain.db.Weights
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.mapToUser(): User {
    return User(
        id = this[Users.id],
        name = this[Users.name],
        email = this[Users.email],
        password = this[Users.password]
    )
}

fun ResultRow.mapToWeight(): Weight {
    return Weight(
        value = this[Weights.weight].toDouble(),
        timestamp = this[Weights.timestamp]
    )
}

fun ResultRow.mapToCardio(): Cardio {
    return Cardio(
        distance = this[Cardios.distance].toDouble(),
        time = this[Cardios.time],
        timestamp = this[Cardios.timestamp]
    )
}