package com.example.dragonballwiki.dragonlist.data.repository

import com.example.dragonballwiki.core.AsyncError
import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.core.RepositoryErrorManager
import com.example.dragonballwiki.core.map
import com.example.dragonballwiki.dragonlist.data.local.CharacterListDao
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DragonListRepositoryImpl(
    private val remoteDataSource: DragonListRemoteDataSource,
    private val localDataSource: DragonListLocalDataSource
) :
    DragonListRepository {
    override suspend fun getCharacterList(): Flow<AsyncResult<CharactersVO>> {
        return flow {
            val localCharacterList = withContext(Dispatchers.IO) { localDataSource.getCharactersList() }
            if (localCharacterList.isNotEmpty()) {
                 emit(AsyncResult.Success(CharactersVO(localCharacterList.toVO())))

            } else {
                val response = getCharacterListRemote()
                response.collect {
                    when (it) {
                        is AsyncResult.Error -> {
                            emit(AsyncResult.Error(AsyncError.ConnectionError))
                        }

                        is AsyncResult.Loading -> {
                            emit(AsyncResult.Loading())
                        }

                        is AsyncResult.Success -> {
                            localDataSource.addCharacters(it.data.characterList.toEntity())
                            emit(withContext(Dispatchers.IO) {
                                    AsyncResult.Success(
                                        CharactersVO(
                                            localDataSource.getCharactersList().toVO()
                                        )
                                    )
                            }
                            )
                        }
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
