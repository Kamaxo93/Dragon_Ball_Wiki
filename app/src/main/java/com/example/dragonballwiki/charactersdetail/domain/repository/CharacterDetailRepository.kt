package com.example.dragonballwiki.charactersdetail.domain.repository

import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface CharacterDetailRepository {
    suspend fun getCharacterDetail(id: String): Flow<AsyncResult<CharacterDetailVO>>
    suspend fun getCharacterDetailLocal(id: String): Flow<CharacterEntity>
}