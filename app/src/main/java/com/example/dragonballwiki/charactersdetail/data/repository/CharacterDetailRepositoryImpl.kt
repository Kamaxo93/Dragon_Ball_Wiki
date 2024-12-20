package com.example.dragonballwiki.charactersdetail.data.repository

import com.example.dragonballwiki.charactersdetail.data.remote.datasource.CharacterDetailRemoteDataSource
import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.core.RepositoryErrorManager
import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

class CharacterDetailRepositoryImpl(
    private val remoteDataSource: CharacterDetailRemoteDataSource,
    private val localDataSource: DragonListLocalDataSource
) :
    CharacterDetailRepository {

    override suspend fun getCharacterDetail(id: String): Flow<AsyncResult<CharacterDetailVO>> =
        RepositoryErrorManager.wrap {
            remoteDataSource.getCharacterDetail(id)
        }

    override suspend fun getCharacterDetailLocal(id: String): Flow<CharacterEntity> {
        return localDataSource.getCharacter(id)
    }

}