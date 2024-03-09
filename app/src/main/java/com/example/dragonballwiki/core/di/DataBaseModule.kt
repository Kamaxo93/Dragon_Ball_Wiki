package com.example.dragonballwiki.core.di

import androidx.room.Room
import com.example.dragonballwiki.core.database.DragonBallDataBase

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataBaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), DragonBallDataBase::class.java, "DragonBallDataBase")
            .build()
    }
    single { get<DragonBallDataBase>().CharacterListDao() }
}