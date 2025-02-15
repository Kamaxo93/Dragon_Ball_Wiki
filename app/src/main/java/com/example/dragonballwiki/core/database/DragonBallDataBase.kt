package com.example.dragonballwiki.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dragonballwiki.charactersdetail.data.local.dao.CharacterDao
import com.example.dragonballwiki.charactersdetail.data.local.dao.TransformationDao
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailEntity
import com.example.dragonballwiki.charactersdetail.data.local.model.TransformationEntity
import com.example.dragonballwiki.dragonlist.data.local.CharacterListDao
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity


@Database(entities = [CharacterEntity::class, CharacterDetailEntity::class, TransformationEntity::class], version = 1)
abstract class DragonBallDataBase : RoomDatabase() {
    abstract fun CharacterListDao(): CharacterListDao
    abstract fun characterDao(): CharacterDao
    abstract fun transformationDao(): TransformationDao
}