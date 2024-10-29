package com.example.dragonballwiki.charactersdetail.domain.usecase

import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.core.AsyncResult
import kotlinx.coroutines.flow.Flow

interface GetCharacterDetailUseCase {
    suspend operator fun invoke(id: String): Flow<AsyncResult<CharacterDetailVO>>
}

class GetCharacterDetailUseCaseImpl(private val repository: CharacterDetailRepository) :
    GetCharacterDetailUseCase {
    override suspend fun invoke(id: String): Flow<AsyncResult<CharacterDetailVO>> {
        return repository.getCharacterDetail(id)
    }
}