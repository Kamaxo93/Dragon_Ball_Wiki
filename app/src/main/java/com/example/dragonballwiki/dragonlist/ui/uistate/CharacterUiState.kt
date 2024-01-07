package com.example.dragonballwiki.dragonlist.ui.uistate

import com.example.dragonballwiki.core.Resource
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import java.lang.Error
import java.lang.Exception

sealed interface CharacterUiState {
    data object Loading : CharacterUiState

    data class Success(val charactersVO: CharactersVO) : CharacterUiState

    data class Error(val error: String?) : CharacterUiState
    data class ErrorGeneric(val throwable: Throwable) : CharacterUiState
}