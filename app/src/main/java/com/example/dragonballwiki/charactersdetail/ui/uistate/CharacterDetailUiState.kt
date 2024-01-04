package com.example.dragonballwiki.charactersdetail.ui.uistate

import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState

sealed interface CharacterDetailUiState {
    data object Loading : CharacterDetailUiState

    data class Success(val character: CharacterDetailVO) : CharacterDetailUiState

    data class Error(val throwable: Throwable) : CharacterDetailUiState

}