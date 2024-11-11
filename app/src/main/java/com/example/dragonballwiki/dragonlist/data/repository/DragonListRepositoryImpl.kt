package com.example.dragonballwiki.dragonlist.data.repository

import com.example.dragonballwiki.core.RepositoryErrorManager
import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO
import com.example.dragonballwiki.dragonlist.domain.model.toBO
import com.example.dragonballwiki.dragonlist.domain.model.toEntity
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DragonListRepositoryImpl(
    private val remoteDataSource: DragonListRemoteDataSource,
    private val localDataSource: DragonListLocalDataSource
) :
    DragonListRepository {

    override suspend fun getCharacterList(): Flow<List<CharacterBO>> {
        val list = localDataSource.getCharactersList().map {
            it.toBO()
        }

        return flow {
            list.collect {
                if (it.isEmpty()) {
                    addCharactersLocalDataBase()

                } else {
                    emit(it)
                }
            }
        }
    }

    private suspend fun addCharactersLocalDataBase() {
        getCharacterListRemote().collect {
                when (it) {
                    is AsyncResult.Success -> {
                        localDataSource.addCharacters(it.data.toEntity())
                    }

                    is AsyncResult.Error -> {
                        throw Exception()
                    }
                    is AsyncResult.Loading -> {

                    }
                }
            }
        }

    private suspend fun getCharacterListRemote(): Flow<AsyncResult<List<CharacterBO>>> =
        RepositoryErrorManager.wrap { remoteDataSource.getCharacterList() }
}
