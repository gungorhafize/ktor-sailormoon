package com.hafize

import com.hafize.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureHTTP()
    configureSerialization()
    configureDatabases()
    configureRouting()
    configureMonitoring()
    configureStaticContent()
}
