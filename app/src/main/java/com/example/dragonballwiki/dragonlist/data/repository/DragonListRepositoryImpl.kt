package com.example.dragonballwiki.dragonlist.data.repository

import com.example.dragonballwiki.core.AsyncError
import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.core.RepositoryErrorManager
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DragonListRepositoryImpl(
    private val remoteDataSource: DragonListRemoteDataSource,
    private val localDataSource: DragonListLocalDataSource
) :
    DragonListRepository {
    override suspend fun getCharacterList(): Flow<CharactersVO> {
        return localDataSource.getCharactersList().map {
            CharactersVO(it.toVO())
        }

    }

    override suspend fun addCharactersLocalDataBase(): Flow<AsyncResult<Unit>> {
        return flow {
            getCharacterListRemote().collect {
                when (it) {
                    is AsyncResult.Success -> {
                        localDataSource.addCharacters(it.data.characterList.toEntity())
                        emit(AsyncResult.Success(Unit))
                    }

                    is AsyncResult.Error -> {
                        emit(AsyncResult.Error(it.error))
                    }

                    is AsyncResult.Loading -> {
                        emit(AsyncResult.Loading())
                    }
                }
            }
        }
    }

    private suspend fun getCharacterListRemote(): Flow<AsyncResult<CharactersVO>> =
        RepositoryErrorManager.wrap { remoteDataSource.getCharacterList() }

    private fun List<CharacterEntity>.toVO(): List<CharacterVO> {
        return this.map {
            CharacterVO(
                affiliation = it.affiliation,
                description = it.description,
                gender = it.gender,
                id = it.id,
                image = it.image,
                ki = it.ki,
                maxKi = it.maxKi,
                name = it.name,
                race = it.race
            )
        }
    }
}

private fun List<CharacterVO>.toEntity(): List<CharacterEntity> {
    return this.map {
        CharacterEntity(
            id = it.id,
            affiliation = it.affiliation,
            description = it.description,
            gender = it.gender,
            image = it.image,
            ki = it.ki,
            maxKi = it.maxKi,
            name = it.name,
            race = it.race
        )
    }
}
