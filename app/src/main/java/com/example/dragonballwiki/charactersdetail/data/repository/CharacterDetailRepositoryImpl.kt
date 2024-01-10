package com.example.dragonballwiki.charactersdetail.data.repository

import com.example.dragonballwiki.charactersdetail.data.datasource.CharacterDetailDataSource
import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.core.RepositoryErrorManager
import kotlinx.coroutines.flow.Flow

class CharacterDetailRepositoryImpl(private val remoteDataSource: CharacterDetailDataSource) :
    CharacterDetailRepository {

    override suspend fun getCharacterDetail(id: String): Flow<AsyncResult<CharacterDetailVO>> =
        RepositoryErrorManager.wrap {
            remoteDataSource.getCharacterDetail(id)
        }
}