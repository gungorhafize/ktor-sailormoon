package com.hafize.models

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val alias: String,
    val birthDate: String,
    val heightCm: Int,
    val powerLevel: Int,
    val specialAttacks: List<String>,
    val description: String,
    val image: String
)