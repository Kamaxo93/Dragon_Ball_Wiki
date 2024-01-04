package com.example.dragonballwiki.charactersdetail.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    getCharacterDetailUseCase: GetCharacterDetailUseCase
) : ViewModel() {



}