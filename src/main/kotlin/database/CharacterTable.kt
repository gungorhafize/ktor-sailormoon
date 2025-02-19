package com.hafize.database

import org.jetbrains.exposed.sql.Table

object CharacterTable : Table("characters") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val alias = varchar("alias", 100).nullable()
    val birthDate = varchar("birth_date", 20)
    val heightCm = integer("height_cm")
    val powerLevel = integer("power_level")
    val specialAttacks = varchar("special_attacks", 255)  // Store as CSV string
    val description = text("description")
    val image = varchar("image", 255)

    override val primaryKey = PrimaryKey(id)

}