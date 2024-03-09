package com.example.dragonballwiki.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dragonballwiki.dragonlist.data.local.CharacterListDao
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity


@Database(entities = [CharacterEntity::class], version = 1)
abstract class DragonBallDataBase : RoomDatabase() {
    abstract fun CharacterListDao(): CharacterListDao
}