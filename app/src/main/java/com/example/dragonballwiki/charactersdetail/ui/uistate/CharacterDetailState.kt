package com.example.dragonballwiki.charactersdetail.ui.uistate

import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO

data class CharacterDetailState(
    val characterDetail: CharacterDetailVO? = null,
    val loading: Boolean = false,
    val error: String? =  null
)