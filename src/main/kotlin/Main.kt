package com.greenmen

import com.greenmen.config.JavalinConfig
import com.greenmen.di.appModule
import org.koin.core.context.GlobalContext.startKoin


fun main() {
    // Koin Manages Dependency Injection
    startKoin {
        modules(appModule())
        JavalinConfig(port = 10000).startJavalinService()
    }
}