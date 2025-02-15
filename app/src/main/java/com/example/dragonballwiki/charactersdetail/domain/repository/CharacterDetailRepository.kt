package com.example.dragonballwiki.charactersdetail.domain.repository

import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailEntity
import com.example.dragonballwiki.charactersdetail.data.local.model.TransformationEntity
import com.example.dragonballwiki.charactersdetail.data.remote.model.CharacterDetailResponse
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.core.async.AsyncResult
import kotlinx.coroutines.flow.Flow

interface CharacterDetailRepository {
    suspend fun getCharacterDetailServer(id: String): Flow<AsyncResult<CharacterDetailResponse>>
    suspend fun getCharacterDetailLocal(id: Int): Flow<CharacterDetailVO>
    suspend fun insertCharacter(characterEntity: CharacterDetailEntity)
    suspend fun insertTransformation(transformationEntity: List<TransformationEntity>)
    suspend fun getTransformation(characterId: Int): Flow<List<TransformationEntity>>
}