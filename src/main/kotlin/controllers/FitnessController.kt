package com.greenmen.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.greenmen.models.AddCardioRequest
import com.greenmen.models.AddWeightRequest
import com.greenmen.models.auth.asJwtToken
import com.greenmen.models.auth.decrypt
import com.greenmen.repository.CardioDAO
import com.greenmen.repository.WeightDAO
import io.javalin.http.Context
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object FitnessController : KoinComponent {
    private val weightDAO: WeightDAO by inject()
    private val cardioDAO: CardioDAO by inject()

    fun getWeights(ctx: Context) {
        val authHeader = ctx.header("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7).asJwtToken()
            val weights = weightDAO.findByUserId(token.decrypt())
            ctx.json(weights)
        } else {
            ctx.status(400).result("Authorization header missing or invalid.")
        }
    }

    fun addWeight(ctx: Context) {
        val authHeader = ctx.header("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7).asJwtToken()
            val weightRequest = ctx.bodyAsClass(AddWeightRequest::class.java)
            println(weightRequest)
            weightDAO.new(weightRequest.weight, token.decrypt())
            ctx.status(200)
        } else {
            ctx.status(400).result("Authorization header missing or invalid.")
        }
    }

    fun deleteWeight(ctx: Context) {
        val authHeader = ctx.header("Authorization")
        val mapper = jacksonObjectMapper()

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7).asJwtToken()
            val weight = mapper.readValue<Float>(ctx.body())
            weightDAO.new(weight.toDouble(), token.decrypt())
            ctx.status(200)
        } else {
            ctx.status(400).result("Authorization header missing or invalid.")
        }
    }

    fun getCardio(ctx: Context) {
        val authHeader = ctx.header("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7).asJwtToken()
            val weights = cardioDAO.findByUserId(token.decrypt())
            ctx.json(weights)
        } else {
            ctx.status(400).result("Authorization header missing or invalid.")
        }
    }

    fun addCardio(ctx: Context) {
        val authHeader = ctx.header("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7).asJwtToken()
            val cardioRequest = ctx.bodyAsClass(AddCardioRequest::class.java)

            cardioDAO.new(cardioRequest.distance, cardioRequest.time, token.decrypt())
            ctx.status(200)
        } else {
            ctx.status(400).result("Authorization header missing or invalid.")
        }
    }

    fun deleteCardio(ctx: Context) {
        val authHeader = ctx.header("Authorization")
        val mapper = jacksonObjectMapper()

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7).asJwtToken()
            val weight = mapper.readValue<Float>(ctx.body())
            weightDAO.new(weight.toDouble(), token.decrypt())
            ctx.status(200)
        } else {
            ctx.status(400).result("Authorization header missing or invalid.")
        }
    }

}