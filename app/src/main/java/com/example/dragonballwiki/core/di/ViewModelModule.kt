package com.example.dragonballwiki.core.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.example.dragonballwiki.dragonlist.ui.viewmodel.DragonListViewModel
import com.example.dragonballwiki.charactersdetail.ui.viewmodel.CharacterDetailViewModel

val viewModelModule = module {
    viewModelOf(::DragonListViewModel)
    viewModelOf(::CharacterDetailViewModel)
}


