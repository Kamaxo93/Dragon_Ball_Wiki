package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCase
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCaseImpl
import com.example.dragonballwiki.dragonlist.domain.usecase.AddCharactersLocalDataBaseUseCase
import com.example.dragonballwiki.dragonlist.domain.usecase.AddCharactersLocalDataBaseUseCaseImpl
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<GetCharacterListUseCase> { GetCharacterListUseCaseImpl(get()) }
    single<AddCharactersLocalDataBaseUseCase> { AddCharactersLocalDataBaseUseCaseImpl(get()) }
    single<GetCharacterDetailUseCase> { GetCharacterDetailUseCaseImpl(get()) }
}