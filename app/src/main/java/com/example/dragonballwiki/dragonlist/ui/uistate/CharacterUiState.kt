package com.example.dragonballwiki.dragonlist.ui.uistate

import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO

sealed interface CharacterUiState {
    data object Loading : CharacterUiState

    data class Success(val charactersVO: CharactersVO) : CharacterUiState

    data class Error(val error: String?) : CharacterUiState
    data object Start : CharacterUiState
}