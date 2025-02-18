package com.example.dragonballwiki.charactersdetail.data.local.datasource

import com.example.dragonballwiki.charactersdetail.data.local.dao.CharacterDao
import com.example.dragonballwiki.charactersdetail.data.local.dao.TransformationDao
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailEntity
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterWithTransformations
import com.example.dragonballwiki.charactersdetail.data.local.model.TransformationEntity
import kotlinx.coroutines.flow.Flow

interface CharacterDetailLocalDataSource {
    suspend fun insertCharacter(character: CharacterDetailEntity)

    suspend fun insertTransformation(transformation: List<TransformationEntity>)

    fun getCharacterWithTransformations(characterId: Int): Flow<CharacterWithTransformations?>

    fun getTransformation(characterId: Int): Flow<List<TransformationEntity>>
}

class CharacterDetailLocalDataSourceImpl(private val characterDao: CharacterDao, private val transformationDao: TransformationDao) : CharacterDetailLocalDataSource {
    override suspend fun insertCharacter(character: CharacterDetailEntity) {
        characterDao.insertCharacter(character)
    }

    override suspend fun insertTransformation(transformation: List<TransformationEntity>) {
        transformationDao.insertTransformation(transformation)
    }

    override fun getCharacterWithTransformations(characterId: Int): Flow<CharacterWithTransformations?> {
        return characterDao.getCharacterWithTransformations(characterId)
    }

    override fun getTransformation(characterId: Int): Flow<List<TransformationEntity>> {
        return transformationDao.getTransformation(characterId)
    }

}