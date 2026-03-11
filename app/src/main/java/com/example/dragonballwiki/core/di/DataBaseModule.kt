package com.example.dragonballwiki.core.di

import androidx.room.Room
import com.example.dragonballwiki.core.Constant.NAME_DATA_BASE
import com.example.dragonballwiki.core.database.DragonBallDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Módulo de base de datos con Koin
 * Usa androidContext() para obtener el Context de la aplicación
 */
val dataBaseModule = module {
    
    // DragonBallDataBase - Singleton
    single {
        Room.databaseBuilder(
            androidContext(),
            DragonBallDataBase::class.java,
            NAME_DATA_BASE
        ).build()
    }
    
    // CharacterListDao - Singleton
    single {
        get<DragonBallDataBase>().CharacterListDao()
    }
}
