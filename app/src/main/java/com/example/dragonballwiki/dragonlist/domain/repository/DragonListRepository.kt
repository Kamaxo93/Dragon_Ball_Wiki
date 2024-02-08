package com.example.dragonballwiki.dragonlist.domain.repository

import com.example.dragonballwiki.core.AsyncResult
import kotlinx.coroutines.flow.Flow

interface DragonListRepository {
    suspend fun getCharacterList(): Flow<CharactersVO>

    suspend fun addCharactersLocalDataBase(): Flow<AsyncResult<Unit>>
}
