package com.example.dragonballwiki.dragonlist.domain.model

import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity

fun List<CharacterEntity>.toBO(): List<CharacterBO> {
    return this.map {
        CharacterBO(
            affiliation = it.affiliation,
            description = it.description,
            gender = it.gender,
            id = it.id,
            image = it.image,
            ki = it.ki,
            maxKi = it.maxKi,
            name = it.name,
            race = it.race
        )
    }
}

fun List<CharacterBO>.toEntity(): List<CharacterEntity> {
    return this.map {
        CharacterEntity(
            id = it.id,
            affiliation = it.affiliation,
            description = it.description,
            gender = it.gender,
            image = it.image,
            ki = it.ki,
            maxKi = it.maxKi,
            name = it.name,
            race = it.race
        )
    }
}