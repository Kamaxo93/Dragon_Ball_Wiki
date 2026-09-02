package com.example.dragonballwiki.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dragonballwiki.charactersdetail.data.local.CharacterDetailDao
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailEntity
import com.example.dragonballwiki.charactersdetail.data.local.model.TransformationEntity
import com.example.dragonballwiki.dragonlist.data.local.CharacterListDao
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity

/**
 * Base de datos Room de Dragon Ball Wiki
 * 
 * Version 2: Agregado sistema de caché para detalles de personajes
 * - CharacterDetailEntity: Detalles completos con timestamp
 * - TransformationEntity: Transformaciones con relación 1:N
 */
@Database(
    entities = [
        CharacterEntity::class,
        CharacterDetailEntity::class,
        TransformationEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class DragonBallDataBase : RoomDatabase() {
    abstract fun CharacterListDao(): CharacterListDao
    abstract fun CharacterDetailDao(): CharacterDetailDao
}