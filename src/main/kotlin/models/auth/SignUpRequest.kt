package com.greenmen.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(val name: String, val email: String, val password: String) {
    companion object
}