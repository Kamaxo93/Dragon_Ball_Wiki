package com.example.dragonballwiki.dragonlist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterListDao {
    @Query("SELECT * from CharacterEntity")
    fun getCharacterList(): Flow<List<CharacterEntity>>

    @Query("SELECT * from CharacterEntity WHERE id = :id")
    fun getCharacter(id: String): Flow<CharacterEntity>

    @Insert
    suspend fun addCharacter(items: List<CharacterEntity>)
}