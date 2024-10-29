package com.example.dragonballwiki.dragonlist.data.local.datasource

import com.example.dragonballwiki.dragonlist.data.local.CharacterListDao
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface DragonListLocalDataSource {
    fun getCharactersList(): Flow<List<CharacterEntity>>

    fun getCharacter(id: String): Flow<CharacterEntity>

    suspend fun addCharacters(characterEntity: List<CharacterEntity>)
}

class DragonListLocalDataSourceImpl(private val dao: CharacterListDao) : DragonListLocalDataSource {
    override fun getCharactersList(): Flow<List<CharacterEntity>> {
        return dao.getCharacterList()
    }

    override fun getCharacter(id: String): Flow<CharacterEntity> {
        return dao.getCharacter(id)
    }

    override suspend fun addCharacters(characterEntity: List<CharacterEntity>) {
        dao.addCharacter(characterEntity)
    }

}