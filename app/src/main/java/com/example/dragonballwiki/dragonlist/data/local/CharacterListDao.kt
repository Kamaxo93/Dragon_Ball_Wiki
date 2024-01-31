package com.example.dragonballwiki.dragonlist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterListDao {
    @Query("SELECT * from CharacterEntity")
    fun getCharacterList(): List<CharacterEntity>

    @Insert
    suspend fun addCharacter(items: List<CharacterEntity>)
}