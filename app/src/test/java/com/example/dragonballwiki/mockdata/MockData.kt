package com.example.dragonballwiki.mockdata

import com.example.dragonballwiki.dragonlist.data.remote.model.CharacterResponse
import com.example.dragonballwiki.dragonlist.data.remote.model.CharactersResponse


fun fillMockDataDragonList() = CharactersResponse(
    listOf(
        CharacterResponse(
            affiliation = "civibus",
            description = "curabitur",
            gender = "mollis",
            id = 4277,
            image = "nascetur",
            ki = "doctus",
            maxKi = "consectetuer",
            name = "Harriett Blevins",
            race = "docendi",
        )
    )
)
