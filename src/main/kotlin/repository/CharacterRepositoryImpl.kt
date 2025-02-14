package com.hafize.repository
import com.hafize.models.Character

class CharacterRepositoryImpl : CharacterRepository {
    private val characters = mutableListOf<Character>()

    override fun getAll(page: Int, pageSize: Int): List<Character> {
        val startIndex = (page - 1) * pageSize
        val endIndex = (page * pageSize).coerceAtMost(characters.size)
        return characters.subList(startIndex, endIndex)
    }
    override fun getById(id: Int): Character? = characters.find { it.id == id }

    override fun add(character: Character): Boolean = characters.add(character)

    override fun delete(id: Int): Boolean = characters.removeIf { it.id == id }
    override fun getTotalCount(): Int { return characters.size }

    override fun searchByName(query: String?): List<Character> {
        val trimmedQuery = query?.trim()
        if (trimmedQuery.isNullOrEmpty()) {
            return characters
        }
        return characters.filter {
            it.name.contains(trimmedQuery, ignoreCase = true) ||
                    it.alias?.contains(trimmedQuery, ignoreCase = true) == true
        }
    }
}