package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.data.remote.datasource.CharacterDetailRemoteDataSource
import com.example.dragonballwiki.charactersdetail.data.remote.datasource.CharacterDetailRemoteDataSourceImpl
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSourceImpl
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<DragonListRemoteDataSource> { DragonListRemoteDataSourceImpl(get()) }
    single<CharacterDetailRemoteDataSource> { CharacterDetailRemoteDataSourceImpl(get()) }
    single<DragonListLocalDataSource> { DragonListLocalDataSourceImpl(get()) }
}