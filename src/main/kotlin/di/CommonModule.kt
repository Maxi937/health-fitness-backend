package com.greenmen.di

import com.greenmen.config.DbConfig
import com.greenmen.models.auth.JwtStaticKey
import com.greenmen.repository.CardioDAO
import com.greenmen.repository.UserDAO
import com.greenmen.repository.WeightDAO
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module
import java.io.FileInputStream
import java.util.Properties

val commonModule = module {
    val properties = Properties()
    val file = FileInputStream("local.properties")
    properties.load(file)

    single<JwtStaticKey>() {
        JwtStaticKey(properties.getProperty("statickey"))
    }

    single<Database>(createdAtStart = true) {
        DbConfig(
            host = properties.getProperty("dbhost"),
            port = properties.getProperty("dbport"),
            name = properties.getProperty("dbname"),
            driver = properties.getProperty("dbdriver"),
            username = properties.getProperty("dbuser"),
            password = properties.getProperty("dbpassword")
        ).getDbConnection()
    }

    single<UserDAO>(createdAtStart = true) { UserDAO() }
    single<WeightDAO>(createdAtStart = true) { WeightDAO() }
    single<CardioDAO>(createdAtStart = true) { CardioDAO() }
}