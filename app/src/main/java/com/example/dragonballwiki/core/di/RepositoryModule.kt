package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.data.repository.CharacterDetailRepositoryImpl
import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.dragonlist.data.repository.DragonListRepositoryImpl
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import org.koin.dsl.module

/**
 * Módulo de repositorios con Koin
 */
val repositoryModule = module {
    
    // DragonListRepository - Singleton
    single<DragonListRepository> {
        DragonListRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }
    
    // CharacterDetailRepository - Singleton
    single<CharacterDetailRepository> {
        CharacterDetailRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }
}
