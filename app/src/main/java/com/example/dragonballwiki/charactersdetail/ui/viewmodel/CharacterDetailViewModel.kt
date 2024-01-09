package com.example.dragonballwiki.charactersdetail.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : ViewModel() {

    fun getCharacterDetail(id: String) {
        viewModelScope.launch {
            getCharacterDetailUseCase(id).collect {

            }
        }
    }
}