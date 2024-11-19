package com.greenmen.repository

import com.greenmen.domain.Cardio
import com.greenmen.domain.db.Cardios
import com.greenmen.domain.db.Weights
import com.greenmen.domain.utils.mapToCardio
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class CardioDAO {

    fun getAll(): List<Cardio> {
        val cardios = mutableListOf<Cardio>()
        transaction {
            Weights.selectAll().forEach {
                cardios.add(it.mapToCardio())
            }
        }
        return cardios
    }

    fun new(distance: Double, time: Long, userId: Int) {
        return transaction {
            Cardios.insert {
                it[Cardios.time] = time
                it[Cardios.distance] = distance.toBigDecimal()
                it[Weights.userId] = userId
            }
        }
    }

    fun findById(id: Int): Cardio? {
        return transaction {
            Cardios.selectAll().where {
                Cardios.id eq id
            }.map { it.mapToCardio() }.firstOrNull()
        }
    }

    fun findByUserId(userId: Int): List<Cardio> {
        return transaction {
            Cardios.selectAll().where {
                Cardios.userId eq userId
            }.map { it.mapToCardio() }
        }
    }
}