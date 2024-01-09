package com.example.dragonballwiki.dragonlist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.core.NetWorkError
import com.example.dragonballwiki.core.Resource
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DragonListViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase

) : ViewModel() {
    private val _uiSate = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val uiState = _uiSate.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(50000),
        CharacterUiState.Loading
    )

    fun dataState() {
        viewModelScope.launch {
            getCharacterListUseCase().collect {
                _uiSate.value = when (it) {
                    is Resource.Success -> Success(
                        it.data ?: CharactersVO(
                            characterList = listOf()
                        )
                    )

                    is Resource.Error -> {
                        when (it) {
                            is NetWorkError -> CharacterUiState.Error(it.message)
                            else -> {
                                CharacterUiState.Error(it.message)
                            }
                        }
                    }

                    is Resource.ErrorsResponse ->
                        CharacterUiState.Error(it.errorResponse?.message)
                }
            }

        }
    }

    fun reloadList() {
        dataState()
    }
}
