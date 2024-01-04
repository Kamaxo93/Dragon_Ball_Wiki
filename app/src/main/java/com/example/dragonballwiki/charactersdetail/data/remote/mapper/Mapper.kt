package com.example.dragonballwiki.charactersdetail.data.remote.mapper

import com.example.dragonballwiki.charactersdetail.data.remote.model.CharacterDetailResponse
import com.example.dragonballwiki.charactersdetail.data.remote.model.OriginPlanetResponse
import com.example.dragonballwiki.charactersdetail.data.remote.model.TransformationResponse
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.charactersdetail.ui.model.OriginPlanet
import com.example.dragonballwiki.charactersdetail.ui.model.Transformation

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
        originPlanet = originPlanet.toVO(),
        race = race,
        transformations = transformations.toVO()
    )

private fun List<TransformationResponse>.toVO(): List<Transformation> =
    this.map {
        Transformation(
            id = it.id, image = it.image, ki = it.ki, name = it.name
        )
    }

private fun OriginPlanetResponse.toVO(): OriginPlanet =
    OriginPlanet(
        description = description, id = id, image = image, isDestroyed = isDestroyed, name = name
    )
