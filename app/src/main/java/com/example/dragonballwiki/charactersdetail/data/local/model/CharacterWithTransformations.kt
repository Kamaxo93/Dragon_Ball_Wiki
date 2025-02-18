package com.example.dragonballwiki.charactersdetail.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithTransformations(
    @Embedded val character: CharacterDetailEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val transformations: List<TransformationEntity>
)
