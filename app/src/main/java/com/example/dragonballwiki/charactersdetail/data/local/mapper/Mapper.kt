package com.example.dragonballwiki.charactersdetail.data.local.mapper

import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailEntity
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailWithTransformations
import com.example.dragonballwiki.charactersdetail.data.local.model.TransformationEntity
import com.example.dragonballwiki.charactersdetail.data.remote.model.CharacterDetailResponse
import com.example.dragonballwiki.charactersdetail.data.remote.model.TransformationResponse
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.charactersdetail.ui.model.Transformation

/**
 * Mappers para conversión entre entidades de Room y modelos de UI/Remote
 */

// ========== Remote Response -> Local Entity ==========

/**
 * Convierte CharacterDetailResponse (API) a CharacterDetailEntity (Room)
 * Agrega timestamp actual para sistema de caché
 */
fun CharacterDetailResponse.toEntity(): CharacterDetailEntity = CharacterDetailEntity(
    id = id,
    affiliation = affiliation,
    description = description,
    gender = gender,
    image = image,
    ki = ki,
    maxKi = maxKi,
    name = name,
    race = race,
    lastUpdated = System.currentTimeMillis()
)

/**
 * Convierte lista de TransformationResponse a lista de TransformationEntity
 * @param characterId ID del personaje padre
 */
fun List<TransformationResponse>.toEntity(characterId: Int): List<TransformationEntity> =
    this.map { transformation ->
        TransformationEntity(
            characterId = characterId,
            id = transformation.id,
            name = transformation.name,
            image = transformation.image,
            ki = transformation.ki
        )
    }

// ========== Local Entity -> UI VO ==========

/**
 * Convierte CharacterDetailWithTransformations (Room) a CharacterDetailVO (UI)
 */
fun CharacterDetailWithTransformations.toVO(): CharacterDetailVO = CharacterDetailVO(
    id = detail.id,
    affiliation = detail.affiliation,
    description = detail.description,
    gender = detail.gender,
    image = detail.image,
    ki = detail.ki,
    maxKi = detail.maxKi,
    name = detail.name,
    race = detail.race,
    transformations = transformations.toVO(),
    isFromCache = true,
    lastUpdated = detail.lastUpdated
)

/**
 * Convierte lista de TransformationEntity a lista de Transformation (UI)
 */
private fun List<TransformationEntity>.toVO(): List<Transformation> =
    this.map { entity ->
        Transformation(
            id = entity.id,
            name = entity.name,
            image = entity.image,
            ki = entity.ki
        )
    }
