package com.example.dragonballwiki.charactersdetail.domain.repository

import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import kotlinx.coroutines.flow.Flow

interface CharacterDetailRepository {
    fun getCharacterDetail(id: String): Flow<CharacterDetailVO>
}