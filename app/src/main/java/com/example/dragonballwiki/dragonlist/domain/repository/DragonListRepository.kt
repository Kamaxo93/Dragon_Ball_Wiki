package com.example.dragonballwiki.dragonlist.domain.repository

import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO
import kotlinx.coroutines.flow.Flow

interface DragonListRepository {
    suspend fun getCharacterList(): Flow<List<CharacterBO>>

    suspend fun addCharactersLocalDataBase(): Flow<AsyncResult<Unit>>
}
