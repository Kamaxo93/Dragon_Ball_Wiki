package com.example.dragonballwiki.dragonlist.data.repository

import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.core.RepositoryErrorManager
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import kotlinx.coroutines.flow.Flow

class DragonListRepositoryImpl(private val remoteDataSource: DragonListRemoteDataSource):
    DragonListRepository {
    override suspend fun getCharacterListRemote(): Flow<AsyncResult<CharactersVO>> =
        RepositoryErrorManager.wrap { remoteDataSource.getCharacterList() }
}