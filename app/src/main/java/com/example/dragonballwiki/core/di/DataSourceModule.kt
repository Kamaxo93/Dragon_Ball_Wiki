package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.data.remote.datasource.CharacterDetailRemoteDataSource
import com.example.dragonballwiki.charactersdetail.data.remote.datasource.CharacterDetailRemoteDataSourceImpl
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSourceImpl
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSourceImpl
import org.koin.dsl.module

/**
 * Módulo de DataSources con Koin
 */
val dataSourceModule = module {
    
    // DragonListRemoteDataSource - Singleton
    single<DragonListRemoteDataSource> {
        DragonListRemoteDataSourceImpl(service = get())
    }
    
    // CharacterDetailRemoteDataSource - Singleton
    single<CharacterDetailRemoteDataSource> {
        CharacterDetailRemoteDataSourceImpl(service = get())
    }
    
    // DragonListLocalDataSource - Singleton
    single<DragonListLocalDataSource> {
        DragonListLocalDataSourceImpl(dao = get())
    }
}
