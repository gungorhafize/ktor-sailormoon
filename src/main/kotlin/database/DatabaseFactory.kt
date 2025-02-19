package com.hafize.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

object DatabaseFactory {
    private val logger = LoggerFactory.getLogger(DatabaseFactory::class.java)
    private lateinit var dataSource: HikariDataSource

    /**
     * Initializes the database connection.
     * Logs the process and handles errors gracefully.
     */
    fun init() {
        logger.info("📡 Attempting to connect to the database...")
        try {
            dataSource = HikariDataSource(HikariConfig().apply {
                jdbcUrl = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/sailormoon_db"
                driverClassName = System.getenv("DB_DRIVER") ?: "org.postgresql.Driver"
                username = System.getenv("DB_USER") ?: "postgres"
                password = System.getenv("DB_PASSWORD") ?: "password123"
                maximumPoolSize = System.getenv("DB_MAX_POOL_SIZE")?.toInt() ?: 10
                isAutoCommit = false
                transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                validate()
            })

            Database.connect(dataSource)
            logger.info("✅ Database connection established successfully!")

            val flyway = Flyway.configure()
                .dataSource(
                    System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/sailormoon_db",
                    System.getenv("DB_USER") ?: "postgres",
                    System.getenv("DB_PASSWORD") ?: "password123"
                )
                .load()
            flyway.migrate()

            logger.info("✅ Flyway migrations completed successfully!")
            logger.info("✅ CharacterTable checked/created successfully!")
        } catch (e: Exception) {
            logger.error("❌ Failed to connect to the database: ${e.message}")
            throw e
        }
    }

    /**
     * Closes the database connection.
     * Logs the process and handles exceptions.
     */
    fun close() {
        try {
            logger.warn("🔴 Closing database connection...")

            if (::dataSource.isInitialized) {
                dataSource.close()
                logger.info("✅ Database connection closed.")
            } else {
                logger.warn("⚠️ Database connection was not initialized.")
            }
        } catch (e: Exception) {
            logger.error("❌ Error closing the database connection: ${e.message}")
        }
    }

    /**
     * Wraps database operations in a transaction.
     * Handles logging of the start and completion of operations.
     */
    fun <T> dbQuery(block: () -> T): T {
        return try {
            logger.debug("⚡️ Starting a new database transaction.")
            val result = transaction { block() }
            logger.debug("✅ Database transaction completed successfully.")
            result
        } catch (e: Exception) {
            logger.error("❌ Database operation failed: ${e.message}")
            throw e
        }
    }
}