package com.hafize.routes

import com.hafize.models.ApiResponse
import com.hafize.models.Character
import com.hafize.repository.CharacterRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Route.characters() {
    val sailorMoonRepository: CharacterRepository by inject()

    get("/sailormoon/characters") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..5) { "Page must be between 1 and 5" }

            val pageSize = 10

            val characters = sailorMoonRepository.getAll(page, pageSize)
            val totalCount = sailorMoonRepository.getTotalCount()

            val totalPages = (totalCount + pageSize - 1) / pageSize
            val prevPage = if (page > 1) page - 1 else null
            val nextPage = if (page < totalPages) page + 1 else null

            val response = ApiResponse(
                success = true,
                prevPage = prevPage,
                nextPage = nextPage,
                sailorMoon = characters
            )

            call.respond(
                status = HttpStatusCode.OK,
                message = response
            )
        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(success = false, message = "Only Numbers Allowed."),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                message = ApiResponse(success = false, message = "Invalid page number."),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: Exception) {
            call.respond(
                message = ApiResponse(success = false, message = "An error occurred."),
                status = HttpStatusCode.InternalServerError
            )
        }
    }

    post("/sailormoon/addcharacter") {
        try {
            val newCharacter = call.receive<Character>()

            if (sailorMoonRepository.add(newCharacter)) {
                call.respond(HttpStatusCode.Created, ApiResponse(success = true, message = "Character added successfully.", sailorMoon = listOf(newCharacter)))
            } else {
                call.respond(HttpStatusCode.InternalServerError, ApiResponse(success = false, message = "Character could not be added."))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest, ApiResponse(success = false, message = "Invalid input data."))
        }
    }

    delete("/sailormoon/characters/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null && sailorMoonRepository.delete(id)) {
            call.respond(HttpStatusCode.OK, ApiResponse(success = true, message = "Character deleted successfully."))
        } else {
            call.respond(HttpStatusCode.NotFound, ApiResponse(success = false, message = "Character not found."))
        }
    }


    // Search for Characters by name
    get("/sailormoon/characters/search") {
        try {
            val nameQuery = call.request.queryParameters["query"]
            val characters = sailorMoonRepository.searchByName(nameQuery)

            if (characters.isNotEmpty()) {
                call.respond(HttpStatusCode.OK, ApiResponse(success = true, sailorMoon = characters))
            } else {
                call.respond(HttpStatusCode.NotFound, ApiResponse(success = false, message = "No characters found"))
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, ApiResponse(success = false, message = "An error occurred during the search."))
        }
    }

}


