package com.example.dragonballwiki.charactersdetail.data.remote.mapper

import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailEntity
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterWithTransformations
import com.example.dragonballwiki.charactersdetail.data.local.model.TransformationEntity
import com.example.dragonballwiki.charactersdetail.data.remote.model.CharacterDetailResponse
import com.example.dragonballwiki.charactersdetail.data.remote.model.TransformationResponse
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.charactersdetail.ui.model.Transformation
import com.example.dragonballwiki.core.Constant.EMPTY
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity

fun CharacterEntity.toVO(): CharacterDetailVO =
    CharacterDetailVO(
        affiliation = affiliation,
        description = description,
        gender = gender,
        id = id,
        image = image,
        ki = ki,
        maxKi = maxKi,
        name = name,
        race = race,
        transformations = emptyList(),
        imagePlanet = EMPTY,
        namePlanet = EMPTY,
        descriptionPlanet = EMPTY
    )

fun CharacterDetailResponse.toEntity(): CharacterDetailEntity =
    CharacterDetailEntity(
        affiliation = affiliation,
        description = description,
        gender = gender,
        id = id,
        image = image,
        ki = ki,
        maxKi = maxKi,
        name = name,
        race = race,
        imagePlanet = originPlanet.image.orEmpty(),
        namePlanet = originPlanet.name.orEmpty(),
        descriptionPlanet = originPlanet.description.orEmpty()
    )

fun List<TransformationResponse>.toEntity(characterId: Int): List<TransformationEntity> =
    this.map {
        TransformationEntity(
            id = it.id, image = it.image, ki = it.ki, name = it.name, characterId = characterId
        )
    }

fun CharacterWithTransformations.toVo(): CharacterDetailVO =
    CharacterDetailVO(
        affiliation = character.affiliation,
        description = character.description,
        gender = character.gender,
        id = character.id,
        image = character.image,
        ki = character.ki,
        maxKi = character.maxKi,
        name = character.name,
        race = character.race,
        transformations = transformations.toVO(),
        imagePlanet = character.imagePlanet,
        namePlanet = character.namePlanet,
        descriptionPlanet = character.descriptionPlanet
    )

fun List<TransformationEntity>.toVO(): List<Transformation> =
    this.map {
        Transformation(
            id = it.id, image = it.image, ki = it.ki, name = it.name
        )
    }