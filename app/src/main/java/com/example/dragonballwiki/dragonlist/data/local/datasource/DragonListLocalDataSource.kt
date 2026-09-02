package com.example.dragonballwiki.dragonlist.data.local.datasource

import android.util.Log
import com.example.dragonballwiki.dragonlist.data.local.CharacterListDao
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface DragonListLocalDataSource {
    fun getCharactersList(): Flow<List<CharacterEntity>>

    fun getCharacter(id: String): Flow<CharacterEntity>

    suspend fun addCharacters(characterEntity: List<CharacterEntity>)
}

class DragonListLocalDataSourceImpl(private val dao: CharacterListDao) : DragonListLocalDataSource {
    
    companion object {
        private const val TAG = "DragonList-LocalDS"
    }
    
    override fun getCharactersList(): Flow<List<CharacterEntity>> {
        Log.d(TAG, "💿 getCharactersList() - Calling Room DAO")
        return dao.getCharacterList()
    }

    override fun getCharacter(id: String): Flow<CharacterEntity> {
        Log.d(TAG, "💿 getCharacter($id) - Calling Room DAO")
        return dao.getCharacter(id)
    }

    override suspend fun addCharacters(characterEntity: List<CharacterEntity>) {
        Log.d(TAG, "💾 addCharacters() - Saving ${characterEntity.size} entities to Room")
        dao.addCharacter(characterEntity)
        Log.d(TAG, "✅ Characters saved to Room successfully")
    }

}