package com.example.dragonballwiki.dragonlist.data.local.datasource

import com.example.dragonballwiki.dragonlist.data.local.CharacterListDao
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface DragonListLocalDataSource {
    fun getCharactersList(): List<CharacterEntity>

    suspend fun addCharacters(characterEntity: CharacterEntity)
}

class DragonListLocalDataSourceImpl(private val dao: CharacterListDao) : DragonListLocalDataSource {
    override fun getCharactersList(): List<CharacterEntity> {
        return dao.getCharacterList()
    }

    override suspend fun addCharacters(characterEntity: CharacterEntity) {
        dao.addCharacter(characterEntity)
    }

}