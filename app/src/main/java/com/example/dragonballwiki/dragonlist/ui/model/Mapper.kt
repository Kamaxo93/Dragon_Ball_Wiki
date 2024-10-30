package com.example.dragonballwiki.dragonlist.ui.model

import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO

fun List<CharacterBO>.toVO() = this.map {
    CharacterVO(
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