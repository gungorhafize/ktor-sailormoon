package com.hafize.repository
import com.hafize.database.CharacterTable
import com.hafize.database.DatabaseFactory.dbQuery
import com.hafize.models.Character
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory


class CharacterRepositoryImpl : CharacterRepository {
    private val logger = LoggerFactory.getLogger(CharacterRepositoryImpl::class.java)

    override suspend fun getAll(page: Int, pageSize: Int): List<Character> = dbQuery {
        try {
            val charactersList = CharacterTable
                .selectAll()
                .limit(pageSize).offset(start = ((page - 1) * pageSize).toLong())
                .map { rowToCharacter(it) }

            logger.info("📜 Page: $page, Size: $pageSize - Retrieved ${charactersList.size} characters.")
            charactersList
        } catch (e: Exception) {
            logger.error("❌ Error occurred while retrieving characters: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getById(id: Int): Character? = dbQuery {
        try {
            val character = CharacterTable
                .selectAll().where { CharacterTable.id eq id }
                .map { rowToCharacter(it) }
                .singleOrNull()

            if (character != null) {
                logger.info("🔍 Character '${character.name}' found.")
            } else {
                logger.warn("⚠️ No character found with ID $id.")
            }
            character
        } catch (e: Exception) {
            logger.error("❌ Error occurred while retrieving character with ID $id: ${e.message}")
            null
        }
    }
    override suspend fun add(character: Character): Boolean = dbQuery {
         try {
            logger.info("🆕 Adding ${character.name} character...")

            val inserted = CharacterTable.insert {
                it[name] = character.name
                it[alias] = character.alias  // alias can be nullable
                it[birthDate] = character.birthDate
                it[heightCm] = character.heightCm
                it[powerLevel] = character.powerLevel
                it[specialAttacks] = character.specialAttacks.joinToString(",")  // Store as comma-separated values
                it[description] = character.description
                it[image] = character.image
            }.insertedCount > 0

            if (inserted) {
                logger.info("✅ ${character.name} was successfully added to the database.")
            } else {
                logger.error("❌ Failed to add ${character.name}!")
            }

            inserted
        } catch (e: Exception) {
            logger.error("❌ Error occurred while adding character '${character.name}': ${e.message}")
            false
        }
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        try {
            val deletedCount = CharacterTable.deleteWhere { CharacterTable.id eq id }
            logger.info("🗑️ Deleted character with ID $id. Deleted count: $deletedCount")
            deletedCount > 0
        } catch (e: Exception) {
            logger.error("❌ Error occurred while deleting character with ID $id: ${e.message}")
            false
        }
    }


    override suspend fun getTotalCount(): Int = dbQuery {
        try {
            val count = CharacterTable
                .selectAll()
                .count().toInt()  // Return the total number of characters

            logger.info("📊 Total number of characters: $count")
            count
        } catch (e: Exception) {
            logger.error("❌ Error occurred while getting total count of characters: ${e.message}")
            0  // Return 0 in case of an error
        }
    }


    override suspend fun searchByName(query: String?): List<Character> = dbQuery {
        try {
            val trimmedQuery = query?.trim()

            if (trimmedQuery.isNullOrEmpty()) {
                // Return all characters if query is empty
                val allCharacters = CharacterTable
                    .selectAll()
                    .map { rowToCharacter(it) }

                logger.info("🔍 Empty query - Retrieved all characters: ${allCharacters.size} characters.")
                return@dbQuery allCharacters
            }

            // Search by name or alias
            val charactersQuery = CharacterTable
                .selectAll().where {
                    (CharacterTable.name.lowerCase() like "%${trimmedQuery.lowercase()}%") or
                            (CharacterTable.alias.isNotNull() and CharacterTable.alias.lowerCase().like("%${trimmedQuery.lowercase()}%"))
                }

            val charactersList = charactersQuery.map { rowToCharacter(it) }
            logger.info("🔍 Searched for '$query' - Found ${charactersList.size} characters.")
            charactersList
        } catch (e: Exception) {
            logger.error("❌ Error occurred while searching for characters by name: ${e.message}")
            emptyList()
        }
    }

    private fun rowToCharacter(row: ResultRow): Character {
        val specialAttacksString = row[CharacterTable.specialAttacks] // assuming it's a text field storing comma-separated values
        val specialAttacksList = specialAttacksString.split(",").map { it.trim() } // Split and trim special attacks

        return Character(
            id = row[CharacterTable.id],
            name = row[CharacterTable.name],
            alias = row[CharacterTable.alias],
            birthDate = row[CharacterTable.birthDate],
            heightCm = row[CharacterTable.heightCm],
            powerLevel = row[CharacterTable.powerLevel],
            specialAttacks = specialAttacksList,  // Mapping to List
            description = row[CharacterTable.description],
            image = row[CharacterTable.image]
        )
    }

}