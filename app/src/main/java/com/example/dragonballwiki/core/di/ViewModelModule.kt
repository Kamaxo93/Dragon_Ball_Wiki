package com.example.dragonballwiki.core.di

import com.example.dragonballwiki.charactersdetail.ui.viewmodel.CharacterDetailViewModel
import com.example.dragonballwiki.dragonlist.ui.viewmodel.DragonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Módulo de ViewModels con Koin
 * Usa viewModel() DSL para integración automática con Compose
 */
val viewModelModule = module {
    
    // DragonListViewModel
    viewModel {
        DragonListViewModel(
            getCharacterListUseCase = get(),
            addCharactersLocalDataBaseUseCase = get()
        )
    }
    
    // CharacterDetailViewModel
    viewModel {
        CharacterDetailViewModel(
            getCharacterDetailUseCase = get(),
            getCharacterDetailErrorUseCase = get(),
            ioDispatcher = get(named(DispatcherQualifiers.IO)),
            savedStateHandle = get()
        )
    }
}
