package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.data.remote.datasource.CharacterDetailRemoteDataSource
import com.example.dragonballwiki.charactersdetail.data.repository.CharacterDetailRepositoryImpl
import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
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
    fun provideDragonListRepository(remoteDatasource: DragonListRemoteDataSource, localDataSource: DragonListLocalDataSource): DragonListRepository {
        return DragonListRepositoryImpl(remoteDatasource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideCharacterDetailRepository(remoteDatasource: CharacterDetailRemoteDataSource): CharacterDetailRepository {
        return CharacterDetailRepositoryImpl(remoteDatasource)
    }
}