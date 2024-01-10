package com.example.dragonballwiki.charactersdetail.domain.repository

import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.core.AsyncResult
import kotlinx.coroutines.flow.Flow

interface CharacterDetailRepository {
    suspend fun getCharacterDetail(id: String): Flow<AsyncResult<CharacterDetailVO>>
}