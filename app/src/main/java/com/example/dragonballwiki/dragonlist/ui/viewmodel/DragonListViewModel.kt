package com.example.dragonballwiki.dragonlist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.core.AsyncError
import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DragonListViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase

) : ViewModel() {
    private val _uiSate = MutableStateFlow<CharacterUiState>(CharacterUiState.Start)
    val uiState = _uiSate.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(50000),
        CharacterUiState.Loading
    )

    fun dataState() {
        viewModelScope.launch(Dispatchers.IO) {
            getCharacterListUseCase().collect {
                _uiSate.value = when (it) {
                    is AsyncResult.Success -> {
                        Success(
                            it.data
                        )
                    }

                    is AsyncResult.Error -> {
                        when (it.error) {
                            AsyncError.ConnectionError -> CharacterUiState.Error("error en la conexíon")
                            is AsyncError.CustomError -> CharacterUiState.Error("error en la conexíon")
                            AsyncError.DataParseError -> CharacterUiState.Error("error en la conexíon")
                            AsyncError.EmptyResponseError -> CharacterUiState.Error("error en la conexíon")
                            is AsyncError.ServerError -> CharacterUiState.Error("error en la conexíon")
                            AsyncError.TimeoutError -> CharacterUiState.Error("error en la conexíon")
                            is AsyncError.UnknownError -> CharacterUiState.Error("error en la conexíon")
                        }
                    }

                    is AsyncResult.Loading -> {
                        CharacterUiState.Loading
                    }
                }
            }

        }
    }

    fun reloadList() {
        dataState()
    }
}
