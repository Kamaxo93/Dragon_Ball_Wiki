package com.example.dragonballwiki.dragonlist.data.repository

import com.example.dragonballwiki.core.AsyncError
import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.core.RepositoryErrorManager
import com.example.dragonballwiki.dragonlist.data.local.CharacterListDao
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DragonListRepositoryImpl(private val remoteDataSource: DragonListRemoteDataSource, private val localDataSource: DragonListLocalDataSource):
    DragonListRepository {
    override suspend fun getCharacterList(): Flow<AsyncResult<CharactersVO>> {

       val localCharacterList = localDataSource.getCharactersList()

       if (localCharacterList.isNotEmpty()) {
           return flow { AsyncResult.Success(CharactersVO(localCharacterList.toVO())) }

       } else {
           val response = getCharacterListRemote()
       }
    }

    private suspend fun getCharacterListRemote(): Flow<AsyncResult<CharactersVO>> = RepositoryErrorManager.wrap { remoteDataSource.getCharacterList() }

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