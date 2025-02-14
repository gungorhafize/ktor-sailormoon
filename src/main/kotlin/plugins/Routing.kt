package com.hafize.plugins

import com.hafize.routes.characters
import com.hafize.routes.root
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        root()
        characters()

    }
    }
