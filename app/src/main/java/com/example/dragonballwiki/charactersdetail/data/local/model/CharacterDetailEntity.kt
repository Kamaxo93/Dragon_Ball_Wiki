package com.example.dragonballwiki.charactersdetail.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter


@Entity
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
    val originPlanetEntity: OriginPlanetEntity,
    val race: String,
    val transformationEntities: List<TransformationEntity>
)