package com.hafize

import com.hafize.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
   embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    configureDatabases()
    configureKoin()
    configureHTTP()
    configureSerialization()
    configureRouting()
    configureMonitoring()
    configureStaticContent()
}
