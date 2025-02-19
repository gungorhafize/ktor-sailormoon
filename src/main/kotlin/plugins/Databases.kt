package com.hafize.plugins

import com.hafize.database.DatabaseFactory
import io.ktor.server.application.*


fun Application.configureDatabases() {
    try {
        DatabaseFactory.init()
        log.info("📡 Database connection initialized.")
    } catch (e: Exception) {
        log.error("❌ Failed to initialize database connection: ${e.message}")
        throw e
    }
}
