package com.hafize.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val alias: String,
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("height_cm")
    val heightCm: Int,
    @SerialName("power_level")
    val powerLevel: Int,
    @SerialName("special_attacks")
    val specialAttacks: List<String>,
    val description: String,
    val image: String
)