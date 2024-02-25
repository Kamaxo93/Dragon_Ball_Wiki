package com.example.dragonballwiki.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dragonballwiki.charactersdetail.data.local.CharacterDetailDao
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailEntity
import com.example.dragonballwiki.charactersdetail.data.local.model.converte.OriginPlanetConverter
import com.example.dragonballwiki.dragonlist.data.local.CharacterListDao
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity


@Database(entities = [CharacterEntity::class, CharacterDetailEntity::class], version = 1)
@TypeConverters(OriginPlanetConverter::class)
abstract class DragonBallDataBase : RoomDatabase() {
    abstract fun CharacterListDao(): CharacterListDao

    abstract fun CharacterDetailDao(): CharacterDetailDao
}