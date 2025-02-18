package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCase
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCaseImpl
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetTransformations
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetTransformationsImpl
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetCharacterListUseCase(repository: DragonListRepository): GetCharacterListUseCase {
        return GetCharacterListUseCaseImpl(repository)
    }

    @Provides
    fun provideGetCharacterDetailUseCase(repository: CharacterDetailRepository): GetCharacterDetailUseCase {
        return GetCharacterDetailUseCaseImpl(repository)
    }

    @Provides
    fun provideGetTransformations(repository: CharacterDetailRepository): GetTransformations {
        return GetTransformationsImpl(repository)
    }
}