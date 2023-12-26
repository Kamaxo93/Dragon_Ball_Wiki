package com.example.dragonballwiki.dragonlist.data.remote.mapper

import com.example.dragonballwiki.dragonlist.data.remote.model.CharacterResponse
import com.example.dragonballwiki.dragonlist.data.remote.model.CharactersResponse
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO


fun CharactersResponse.toVo(): CharactersVO = CharactersVO(
    characterList = characters.map { it.toVO() }
)

fun CharacterResponse.toVO(): CharacterVO = CharacterVO(
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