package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.data.repository.CharacterDetailRepositoryImpl
import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.dragonlist.data.repository.DragonListRepositoryImpl
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<DragonListRepository>{DragonListRepositoryImpl(get(), get())}
    single<CharacterDetailRepository> {CharacterDetailRepositoryImpl(get())}
}