package com.example.dragonballwiki.charactersdetail.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidad de Room para almacenar transformaciones de personajes
 * Relación N:1 con CharacterDetailEntity
 */
@Entity(
    tableName = "transformation",
    foreignKeys = [
        ForeignKey(
            entity = CharacterDetailEntity::class,
            parentColumns = ["id"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["characterId"])
    ]
)
data class TransformationEntity(
    @PrimaryKey(autoGenerate = true)
    val transformationId: Int = 0,
    val characterId: Int,
    val id: Int,
    val name: String,
    val image: String,
    val ki: String
)
