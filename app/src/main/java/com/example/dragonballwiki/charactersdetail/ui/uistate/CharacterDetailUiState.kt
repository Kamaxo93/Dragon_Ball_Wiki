package com.example.dragonballwiki.charactersdetail.ui.uistate

import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO

sealed interface CharacterDetailUiState {
    data object Loading : CharacterDetailUiState

    data object Start : CharacterDetailUiState

    data class Success(val character: CharacterDetailVO) : CharacterDetailUiState

    data class Error(val error: String) : CharacterDetailUiState

}