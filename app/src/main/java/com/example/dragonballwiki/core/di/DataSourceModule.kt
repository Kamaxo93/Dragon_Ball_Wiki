package com.example.dragonballwiki.core.di

import android.content.Context
import com.example.dragonballwiki.charactersdetail.data.datasource.CharacterDetailDataSource
import com.example.dragonballwiki.charactersdetail.data.datasource.CharacterDetailDataSourceImpl
import com.example.dragonballwiki.charactersdetail.data.remote.service.CharacterDetailService
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSourceImpl
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        service: CharacterDetailService,
    ): CharacterDetailDataSource {
        return CharacterDetailDataSourceImpl(service)
    }

}