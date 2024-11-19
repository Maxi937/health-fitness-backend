package com.greenmen.repository

import com.greenmen.domain.Weight
import com.greenmen.domain.db.Weights
import com.greenmen.domain.utils.mapToWeight
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class WeightDAO {

    fun getAll(): List<Weight> {
        val weights = mutableListOf<Weight>()
        transaction {
            Weights.selectAll().forEach {
                weights.add(it.mapToWeight())
            }
        }
        return weights
    }

    fun new(value: Double, userId: Int) {
        return transaction {
            Weights.insert {
                it[weight] = value.toBigDecimal()
                it[Weights.userId] = userId
            }
        }
    }

    fun findById(id: Int): Weight? {
        return transaction {
            Weights.selectAll().where {
                Weights.id eq id
            }.map { it.mapToWeight() }.firstOrNull()
        }
    }

    fun findByUserId(userId: Int): List<Weight> {
        return transaction {
            Weights.selectAll().where {
                Weights.userId eq userId
            }.map { it.mapToWeight() }
        }
    }
}