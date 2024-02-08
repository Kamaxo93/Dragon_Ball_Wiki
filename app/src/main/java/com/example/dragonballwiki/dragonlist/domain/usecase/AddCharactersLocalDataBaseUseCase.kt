package com.example.dragonballwiki.dragonlist.domain.usecase

import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import kotlinx.coroutines.flow.Flow

interface AddCharactersLocalDataBaseUseCase {
    suspend operator fun invoke(): Flow<AsyncResult<Unit>>
}

class AddCharactersLocalDataBaseUseCaseImpl(private val repository: DragonListRepository): AddCharactersLocalDataBaseUseCase {
    override suspend fun invoke(): Flow<AsyncResult<Unit>> {
        return repository.addCharactersLocalDataBase()
    }
}