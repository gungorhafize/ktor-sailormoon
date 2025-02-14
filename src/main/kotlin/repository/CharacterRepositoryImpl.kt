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
}