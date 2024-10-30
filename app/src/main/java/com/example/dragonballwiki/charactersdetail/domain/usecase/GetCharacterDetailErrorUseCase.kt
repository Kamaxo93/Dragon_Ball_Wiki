package com.example.dragonballwiki.charactersdetail.domain.usecase

import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface GetCharacterDetailErrorUseCase {
    suspend operator fun invoke(id: String): Flow<CharacterEntity>
}

class GetCharacterDetailErrorUseCaseImpl(private val repository: CharacterDetailRepository) :
    GetCharacterDetailErrorUseCase {
    override suspend fun invoke(id: String): Flow<CharacterEntity> {
        return repository.getCharacterDetailLocal(id)
    }

}