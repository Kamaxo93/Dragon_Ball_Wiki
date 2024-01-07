package com.example.dragonballwiki.dragonlist.data.repository

import com.example.dragonballwiki.core.Resource
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DragonListRepositoryImpl(private val remoteDataSource: DragonListRemoteDataSource):
    DragonListRepository {
    override fun getCharacterListRemote(): Flow<Resource<CharactersVO>> = flow {
        emit(remoteDataSource.getCharacterList())
    }
}