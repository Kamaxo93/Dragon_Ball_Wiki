package com.example.dragonballwiki.charactersdetail.domain.usecase

import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import kotlinx.coroutines.flow.Flow

interface GetCharacterDetailUseCase {
    operator fun invoke(id: String): Flow<CharacterDetailVO>
}

class GetCharacterDetailUseCaseImpl(private val repository: CharacterDetailRepository): GetCharacterDetailUseCase {
    override fun invoke(id: String): Flow<CharacterDetailVO> {
        return repository.getCharacterDetail(id)
    }
}