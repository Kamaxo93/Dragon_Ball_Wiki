package com.example.dragonballwiki.dragonlist.domain.usecase

import com.example.dragonballwiki.core.Resource
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import kotlinx.coroutines.flow.Flow

interface GetCharacterListUseCase {
    operator fun invoke(): Flow<Resource<CharactersVO>>
}

class GetCharacterListUseCaseImpl(private val repository: DragonListRepository): GetCharacterListUseCase {
    override fun invoke(): Flow<Resource<CharactersVO>> {
        return repository.getCharacterListRemote()
    }

}