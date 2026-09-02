package com.example.dragonballwiki.dragonlist.data.remote.datasource

import android.util.Log
import com.example.dragonballwiki.core.Constant.DEFAULT_CHARACTER_LIMIT
import com.example.dragonballwiki.dragonlist.data.remote.mapper.toBo
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO

interface DragonListRemoteDataSource {
    suspend fun getCharacterList(): List<CharacterBO>
}

class DragonListRemoteDataSourceImpl(
    private val service: DragonListService,
) : DragonListRemoteDataSource {
    
    companion object {
        private const val TAG = "DragonList-RemoteDS"
    }
    
    override suspend fun getCharacterList(): List<CharacterBO> {
        Log.d(TAG, "🌐 getCharacterList() - Calling Retrofit service with limit=$DEFAULT_CHARACTER_LIMIT")
        try {
            val response = service.getCharacterList(DEFAULT_CHARACTER_LIMIT)
            Log.i(TAG, "✅ Retrofit call successful - Received ${response.characters.size} characters")
            val result = response.characters.map { it.toBo() }
            Log.d(TAG, "🔄 Mapped ${result.size} DTOs to BOs")
            return result
        } catch (e: Exception) {
            Log.e(TAG, "❌ Exception during Retrofit call", e)
            throw e
        }
    }
}