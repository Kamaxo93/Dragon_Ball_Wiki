package com.example.dragonballwiki.charactersdetail.data.remote.mapper

import com.example.dragonballwiki.charactersdetail.data.remote.model.CharacterDetailResponse
import com.example.dragonballwiki.charactersdetail.data.remote.model.TransformationResponse
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.charactersdetail.ui.model.Transformation
import com.example.dragonballwiki.core.Constant.EMPTY
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity

fun CharacterDetailResponse.toVO(): CharacterDetailVO =
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
        transformations = transformations.toVO(),
        imagePlanet = originPlanet.image.orEmpty(),
        namePlanet = originPlanet.name.orEmpty(),
        descriptionPlanet = originPlanet.description.orEmpty()
    )

private fun List<TransformationResponse>.toVO(): List<Transformation> =
    this.map {
        Transformation(
            id = it.id, image = it.image, ki = it.ki, name = it.name
        )
    }

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