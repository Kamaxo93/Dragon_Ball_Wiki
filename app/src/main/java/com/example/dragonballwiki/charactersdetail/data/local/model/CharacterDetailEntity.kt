package com.example.dragonballwiki.charactersdetail.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.dragonballwiki.charactersdetail.data.local.model.converte.OriginPlanetConverter
import com.google.gson.annotations.Expose


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
    @TypeConverters(OriginPlanetConverter::class)
    val originPlanetEntity: OriginPlanetEntity,
    val race: String,
//    val transformationEntities: List<TransformationEntity>
)