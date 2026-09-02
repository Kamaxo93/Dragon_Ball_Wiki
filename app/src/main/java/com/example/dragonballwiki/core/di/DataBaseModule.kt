package com.example.dragonballwiki.core.di

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dragonballwiki.core.Constant.NAME_DATA_BASE
import com.example.dragonballwiki.core.database.DragonBallDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Migración de v1 a v2: Agrega tablas de caché para detalles de personajes
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Crear tabla character_detail
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS character_detail (
                id INTEGER PRIMARY KEY NOT NULL,
                affiliation TEXT NOT NULL,
                description TEXT NOT NULL,
                gender TEXT NOT NULL,
                image TEXT NOT NULL,
                ki TEXT NOT NULL,
                maxKi TEXT NOT NULL,
                name TEXT NOT NULL,
                race TEXT NOT NULL,
                lastUpdated INTEGER NOT NULL
            )
        """.trimIndent())
        
        // Crear tabla transformation
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS transformation (
                transformationId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                characterId INTEGER NOT NULL,
                id INTEGER NOT NULL,
                name TEXT NOT NULL,
                image TEXT NOT NULL,
                ki TEXT NOT NULL,
                FOREIGN KEY(characterId) REFERENCES character_detail(id) ON DELETE CASCADE
            )
        """.trimIndent())
        
        // Crear índice para mejorar performance de queries por characterId
        db.execSQL("""
            CREATE INDEX IF NOT EXISTS index_transformation_characterId 
            ON transformation(characterId)
        """.trimIndent())
    }
}

/**
 * Módulo de base de datos con Koin
 * Usa androidContext() para obtener el Context de la aplicación
 */
val dataBaseModule = module {
    
    // DragonBallDataBase - Singleton con migración
    single {
        Room.databaseBuilder(
            androidContext(),
            DragonBallDataBase::class.java,
            NAME_DATA_BASE
        )
        .addMigrations(MIGRATION_1_2)
        .fallbackToDestructiveMigration() // Solo para desarrollo, quitar en producción
        .build()
    }
    
    // CharacterListDao - Singleton
    single {
        get<DragonBallDataBase>().CharacterListDao()
    }
    
    // CharacterDetailDao - Singleton
    single {
        get<DragonBallDataBase>().CharacterDetailDao()
    }
}
