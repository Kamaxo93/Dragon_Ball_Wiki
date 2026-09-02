package com.example.dragonballwiki.charactersdetail.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de Room para almacenar detalles completos de personajes
 * Incluye timestamp para sistema de caché inteligente (10 días)
 */
@Entity(tableName = "character_detail")
data class CharacterDetailEntity(
    @PrimaryKey
    val id: Int,
    val affiliation: String,
    val description: String,
    val gender: String,
    val image: String,
    val ki: String,
    val maxKi: String,
    val name: String,
    val race: String,
    val lastUpdated: Long // Timestamp en milisegundos
)
