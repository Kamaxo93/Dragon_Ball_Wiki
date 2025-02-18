package com.example.dragonballwiki.charactersdetail.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "charactersDetail")
data class CharacterDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val race: String,
    val gender: String,
    val ki: String,
    val maxKi: String,
    val affiliation: String,
    val description: String,
    val image: String,
    val namePlanet: String,
    val descriptionPlanet: String,
    val imagePlanet: String,
)

@Entity(
    tableName = "transformations",
    foreignKeys = [
        ForeignKey(
            entity = CharacterDetailEntity::class,
            parentColumns = ["id"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TransformationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val ki: String,
    val image: String,
    @ColumnInfo(index = true) val characterId: Int // Clave foránea
)