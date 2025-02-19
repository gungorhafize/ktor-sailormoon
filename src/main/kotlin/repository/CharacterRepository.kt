package com.hafize.repository

import com.hafize.models.Character

interface CharacterRepository {
    suspend fun getAll(page: Int, pageSize: Int): List<Character>
    suspend fun getById(id: Int): Character?
    suspend fun add(character: Character): Boolean
    suspend fun delete(id: Int): Boolean
    suspend fun getTotalCount(): Int
    suspend fun searchByName(query: String?): List<Character>

}