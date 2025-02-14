package com.hafize.routes

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.root(){
    get("/"){
        call.respond(
            message = "Welcome to Sailor Moon API",
            status = HttpStatusCode.OK
        )
    }
}