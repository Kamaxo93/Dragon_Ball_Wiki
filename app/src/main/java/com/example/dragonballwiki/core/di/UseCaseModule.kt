package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailErrorUseCase
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailErrorUseCaseImpl
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCase
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCaseImpl
import com.example.dragonballwiki.dragonlist.domain.usecase.AddCharactersLocalDataBaseUseCase
import com.example.dragonballwiki.dragonlist.domain.usecase.AddCharactersLocalDataBaseUseCaseImpl
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCaseImpl
import org.koin.dsl.module

/**
 * Módulo de Use Cases con Koin
 * Los Use Cases son Factory (nueva instancia cada vez) en lugar de Singleton
 */
val useCaseModule = module {
    
    // GetCharacterListUseCase - Factory
    factory<GetCharacterListUseCase> {
        GetCharacterListUseCaseImpl(repository = get())
    }
    
    // AddCharactersLocalDataBaseUseCase - Factory
    factory<AddCharactersLocalDataBaseUseCase> {
        AddCharactersLocalDataBaseUseCaseImpl(repository = get())
    }
    
    // GetCharacterDetailUseCase - Factory
    factory<GetCharacterDetailUseCase> {
        GetCharacterDetailUseCaseImpl(repository = get())
    }
    
    // GetCharacterDetailErrorUseCase - Factory
    factory<GetCharacterDetailErrorUseCase> {
        GetCharacterDetailErrorUseCaseImpl(repository = get())
    }
}
