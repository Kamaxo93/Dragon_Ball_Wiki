package com.example.dragonballwiki.dragonlist.domain.usecase

import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import kotlinx.coroutines.flow.Flow

interface GetCharacterListUseCase {
    suspend operator fun invoke(): Flow<List<CharacterBO>>
}

class GetCharacterListUseCaseImpl(private val repository: DragonListRepository) :
    GetCharacterListUseCase {
    override suspend fun invoke(): Flow<List<CharacterBO>> {
        return repository.getCharacterList()
    }

}