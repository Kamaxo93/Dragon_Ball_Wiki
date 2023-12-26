package com.example.dragonballwiki.dragonlist.domain.repository

import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface DragonListRepository {

    fun getCharacterListRemote(): Flow<CharactersVO>
}

class DragonListRepositoryImpl(private val remoteDataSource: DragonListRemoteDataSource): DragonListRepository {
    override fun getCharacterListRemote(): Flow<CharactersVO> = flow {
        remoteDataSource.getCharacterList()
    }

}
