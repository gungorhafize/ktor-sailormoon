package com.hafize.plugins

import com.hafize.routes.characters
import com.hafize.routes.root
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        root()
        characters()
    }
}
