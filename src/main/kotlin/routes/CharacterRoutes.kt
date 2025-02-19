package com.hafize.routes

import com.hafize.models.ApiResponse
import com.hafize.models.Character
import com.hafize.repository.CharacterRepository
import com.hafize.repository.CharacterRepositoryImpl
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory

fun Route.characters() {
    val sailorMoonRepository: CharacterRepository by inject()
    val logger = LoggerFactory.getLogger(CharacterRepositoryImpl::class.java)

// Get all characters with pagination
    get("/sailormoon/characters") {
        try {
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val pageSize = 10

            // Validate page number
            require(page in 1..5) { "Page must be between 1 and 5" }

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

            call.respond(HttpStatusCode.OK, response)
        } catch (e: NumberFormatException) {
            logger.error("Invalid page number format: ${e.message}")
            call.respond(
                message = ApiResponse(success = false, message = "Page parameter must be a valid number."),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {
            logger.error("Invalid page range: ${e.message}")
            call.respond(
                message = ApiResponse(success = false, message = e.message ?: "Invalid page number."),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: Exception) {
            logger.error("An unexpected error occurred: ${e.message}")
            call.respond(
                message = ApiResponse(success = false, message = "An error occurred."),
                status = HttpStatusCode.InternalServerError
            )
        }
    }


    // Add a new character
    post("/sailormoon/addcharacter") {
        try {
            val newCharacter = call.receive<Character>()

            if (sailorMoonRepository.add(newCharacter)) {
                call.respond(HttpStatusCode.Created, ApiResponse(success = true, message = "Character added successfully.", sailorMoon = listOf(newCharacter)))
            } else {
                call.respond(HttpStatusCode.InternalServerError, ApiResponse(success = false, message = "Character could not be added."))
            }
        } catch (e: Exception) {
            logger.error("Failed to add character: ${e.message}")
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest, ApiResponse(success = false, message = "Invalid input data."))
        }
    }

    // Delete a character by ID
    delete("/sailormoon/characters/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null && sailorMoonRepository.delete(id)) {
            call.respond(HttpStatusCode.OK, ApiResponse(success = true, message = "Character deleted successfully."))
        } else {
            call.respond(HttpStatusCode.NotFound, ApiResponse(success = false, message = "Character not found."))
        }
    }


    // Search for characters by name
    get("/sailormoon/characters/search") {
        try {
            val nameQuery = call.request.queryParameters["query"]
            if (nameQuery.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, ApiResponse(success = false, message = "Query parameter is required for search."))
                return@get
            }

            val characters = sailorMoonRepository.searchByName(nameQuery)

            if (characters.isNotEmpty()) {
                call.respond(HttpStatusCode.OK, ApiResponse(success = true, sailorMoon = characters))
            } else {
                call.respond(HttpStatusCode.NotFound, ApiResponse(success = false, message = "No characters found"))
            }
        } catch (e: Exception) {
            logger.error("Search error: ${e.message}")
            call.respond(HttpStatusCode.InternalServerError, ApiResponse(success = false, message = "An error occurred during the search."))
        }
    }
}


