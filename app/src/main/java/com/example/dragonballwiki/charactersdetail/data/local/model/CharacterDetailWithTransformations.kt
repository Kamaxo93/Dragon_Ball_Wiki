package com.example.dragonballwiki.charactersdetail.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Data class para obtener CharacterDetailEntity con sus transformaciones
 * Usa @Relation de Room para mapeo automático
 */
data class CharacterDetailWithTransformations(
    @Embedded
    val detail: CharacterDetailEntity,
    
    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val transformations: List<TransformationEntity>
)
