package com.greenmen.models.auth

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class JwtStaticKey(from: String) {
    private val key: SecretKey

    init {
        val keyBytes = from.toByteArray(Charsets.UTF_8)
        key = SecretKeySpec(keyBytes, "AES")
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun encrypt(id: Int): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)

        val encryptedBytes = cipher.doFinal(id.toString().toByteArray(Charsets.UTF_8))
        return Base64.encode(encryptedBytes)
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun decrypt(encryptedId: String): Int {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key)

        println(encryptedId)

        val decryptedBytes = cipher.doFinal(Base64.decode(encryptedId))
        return String(decryptedBytes, Charsets.UTF_8).toInt()
    }
}