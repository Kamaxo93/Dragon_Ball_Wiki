package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.data.local.dao.CharacterDao
import com.example.dragonballwiki.charactersdetail.data.local.dao.TransformationDao
import com.example.dragonballwiki.charactersdetail.data.local.datasource.CharacterDetailLocalDataSource
import com.example.dragonballwiki.charactersdetail.data.local.datasource.CharacterDetailLocalDataSourceImpl
import com.example.dragonballwiki.charactersdetail.data.remote.datasource.CharacterDetailRemoteDataSource
import com.example.dragonballwiki.charactersdetail.data.remote.datasource.CharacterDetailRemoteDataSourceImpl
import com.example.dragonballwiki.charactersdetail.data.remote.service.CharacterDetailService
import com.example.dragonballwiki.dragonlist.data.local.CharacterListDao
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSourceImpl
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSourceImpl
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideDragonListRemoteDataSource(
        service: DragonListService
    ): DragonListRemoteDataSource {
        return DragonListRemoteDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideCharacterDetailRemoteDataSource(
        service: CharacterDetailService
    ): CharacterDetailRemoteDataSource {
        return CharacterDetailRemoteDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideDragonListLocalDataSource(
        dao: CharacterListDao
    ): DragonListLocalDataSource {
        return DragonListLocalDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideCharacterDetailLocalDataSource(
        characterDao: CharacterDao,
        transformationDao: TransformationDao
    ): CharacterDetailLocalDataSource {
        return CharacterDetailLocalDataSourceImpl(characterDao, transformationDao)
    }
}