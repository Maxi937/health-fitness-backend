package com.greenmen.models.auth

import org.koin.java.KoinJavaComponent.inject
import kotlin.uuid.ExperimentalUuidApi

class JwtToken(
    val token: String,
) {

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun New(userId: Int): JwtToken {
            val key: JwtStaticKey by inject(JwtStaticKey::class.java)
            val token = key.encrypt(userId)
            return JwtToken(
                token = token,
            )
        }
    }
}

fun String.asJwtToken(): JwtToken {
    return JwtToken(this)
}


fun JwtToken.decrypt(): Int {
    val key: JwtStaticKey by inject(JwtStaticKey::class.java)
    val userId = key.decrypt(this.token)
    return userId.toInt()
}