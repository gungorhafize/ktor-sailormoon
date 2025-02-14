package com.hafize.plugins

import com.hafize.di.appModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(){
    install(Koin){
        modules(appModule)
        slf4jLogger()
    }
}