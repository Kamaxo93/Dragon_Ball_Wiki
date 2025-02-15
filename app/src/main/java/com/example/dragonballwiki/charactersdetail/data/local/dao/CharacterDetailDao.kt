package com.example.dragonballwiki.charactersdetail.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailEntity
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterWithTransformations
import com.example.dragonballwiki.charactersdetail.data.local.model.TransformationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransformation(transformation: List<TransformationEntity>)

    @Transaction
    @Query("SELECT * FROM charactersdetail WHERE id = :characterId")
    fun getCharacterWithTransformations(characterId: Int): Flow<CharacterWithTransformations?>

    @Transaction
    suspend fun insertCharacterWithTransformations(character: CharacterDetailEntity, transformation: List<TransformationEntity>) {
        insertCharacter(character)
        insertTransformation(transformation)
    }
}

@Dao
interface TransformationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransformation(transformation: List<TransformationEntity>)

    @Query("SELECT * FROM transformations WHERE characterId = :characterId")
    fun getTransformation(characterId: Int): Flow<List<TransformationEntity>>

}