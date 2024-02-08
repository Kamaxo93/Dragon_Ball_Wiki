package com.example.dragonballwiki.dragonlist.data.remote.mapper

import com.example.dragonballwiki.dragonlist.data.remote.model.CharacterResponse
import com.example.dragonballwiki.dragonlist.data.remote.model.CharactersResponse
import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO

fun CharacterResponse.toBo(): CharacterBO = CharacterBO(
    affiliation = affiliation,
    description = description,
    gender = gender,
    id = id,
    image = image,
    ki = ki,
    maxKi = maxKi,
    name = name,
    race = race
)