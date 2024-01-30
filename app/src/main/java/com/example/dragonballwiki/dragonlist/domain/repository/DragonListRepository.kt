package com.example.dragonballwiki.dragonlist.domain.repository

import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import kotlinx.coroutines.flow.Flow

interface DragonListRepository {
    suspend fun getCharacterList(): Flow<AsyncResult<CharactersVO>>
}
