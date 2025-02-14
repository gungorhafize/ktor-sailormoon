package com.hafize.repository

import com.hafize.models.Character

interface CharacterRepository {
    fun getAll(page: Int, pageSize: Int): List<Character>
    fun getById(id: Int): Character?
    fun add(character: Character): Boolean
    fun delete(id: Int): Boolean
    fun getTotalCount(): Int

}