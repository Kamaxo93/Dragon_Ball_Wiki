package com.example.dragonballwiki.dragonlist.domain.usecase

import android.util.Log
import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import kotlinx.coroutines.flow.Flow

interface GetCharacterListUseCase {
    suspend operator fun invoke(): Flow<List<CharacterBO>>
}

class GetCharacterListUseCaseImpl(private val repository: DragonListRepository) :
    GetCharacterListUseCase {
    
    companion object {
        private const val TAG = "DragonList-UseCase"
    }
    
    override suspend fun invoke(): Flow<List<CharacterBO>> {
        Log.d(TAG, "🎯 GetCharacterListUseCase invoked - Calling repository")
        return repository.getCharacterList()
    }

}