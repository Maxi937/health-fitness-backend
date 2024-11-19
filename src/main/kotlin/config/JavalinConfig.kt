package com.greenmen.config

import com.greenmen.controllers.AuthController
import com.greenmen.controllers.FitnessController
import io.javalin.Javalin

class JavalinConfig(val port: Int) {
    fun startJavalinService(): Javalin {
        return Javalin.create().apply {
            exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 - Not Found") }
            registerRoutes(this)
        }.start(port)
    }

    private fun registerRoutes(app: Javalin) {
        app.post("/api/signup", AuthController::signup)
        app.post("/api/authenticate", AuthController::authenticate)
        app.get("/api/users", AuthController::getAllUsers)
        app.get("/api/users/{user-id}", AuthController::getUserByUserId)
        app.post("/api/users", AuthController::addUser)

        app.get("/api/weight", FitnessController::getWeights)
        app.post("/api/weight", FitnessController::addWeight)
        app.delete("/api/weight", FitnessController::deleteWeight)

        app.get("/api/cardio", FitnessController::getCardio)
        app.post("/api/cardio", FitnessController::addCardio)
        app.delete("/api/cardio", FitnessController::deleteCardio)
    }
}