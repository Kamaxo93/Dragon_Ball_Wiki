package com.example.dragonballwiki.dragonlist.domain.usecase

import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import kotlinx.coroutines.flow.Flow

interface GetCharacterListUseCase {
    suspend operator fun invoke(): Flow<AsyncResult<CharactersVO>>
}

class GetCharacterListUseCaseImpl(private val repository: DragonListRepository): GetCharacterListUseCase {
    override suspend fun invoke(): Flow<AsyncResult<CharactersVO>> {
        return repository.getCharacterList()
    }

}