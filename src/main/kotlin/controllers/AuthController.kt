package com.greenmen.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.greenmen.domain.User
import com.greenmen.models.auth.AuthRequest
import com.greenmen.models.auth.JwtToken
import com.greenmen.models.auth.SignUpRequest
import com.greenmen.repository.UserDAO
import io.javalin.http.Context
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.uuid.ExperimentalUuidApi

object AuthController : KoinComponent {
    private val userDao: UserDAO by inject()

    fun signup(ctx: Context) {
        println("Attempting Signup")
        val signUpRequest = ctx.bodyAsClass(SignUpRequest::class.java)

        userDao.new(email = signUpRequest.email, password = signUpRequest.password, name = signUpRequest.name)
        ctx.status(200)
        ctx.result()
    }

    @OptIn(ExperimentalUuidApi::class)
    fun authenticate(ctx: Context) {
        println("Attempting Authentication")
        val authRequest = ctx.bodyAsClass(AuthRequest::class.java)

        val user = userDao.findByEmail(authRequest.email)

        if (user == null) {
            ctx.status(400)
            ctx.result("User not found")
        } else {
            if (user.password == authRequest.password) {
                println("user password pass")
                ctx.status(200)
                ctx.json(JwtToken.New(user.id))
            }
        }
    }

    fun getAllUsers(ctx: Context) {
        ctx.json(userDao.getAll())
    }

    fun addUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<User>(ctx.body())
        userDao.save(user)
        ctx.json(user)
    }

    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
        }
    }

    fun getUserByUserByEmail(ctx: Context) {
        val user = userDao.findByEmail(ctx.pathParam("user-email"))
        if (user != null) {
            ctx.json(user)
        }
    }

    fun checkAuth(ctx: Context) {

    }


}