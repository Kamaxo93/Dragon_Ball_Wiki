package com.example.dragonballwiki.dragonlist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DragonListViewModel @Inject constructor(
    getCharacterListUseCase: GetCharacterListUseCase
) : ViewModel() {

    val uiState: StateFlow<CharacterUiState> = getCharacterListUseCase().map(::Success)
        .catch { CharacterUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CharacterUiState.Loading)
}