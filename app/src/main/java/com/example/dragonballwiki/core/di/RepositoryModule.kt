package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.data.datasource.CharacterDetailDataSource
import com.example.dragonballwiki.charactersdetail.data.repository.CharacterDetailRepositoryImpl
import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.data.repository.DragonListRepositoryImpl
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideDragonListRepository(remoteDatasource: DragonListRemoteDataSource): DragonListRepository {
        return DragonListRepositoryImpl(remoteDatasource)
    }

    @Provides
    @Singleton
    fun provideCharacterDetailRepository(remoteDatasource: CharacterDetailDataSource): CharacterDetailRepository {
        return CharacterDetailRepositoryImpl(remoteDatasource)
    }
}