package com.example.dragonballwiki.charactersdetail.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCase
import com.example.dragonballwiki.charactersdetail.ui.uistate.CharacterDetailUiState
import com.example.dragonballwiki.core.AsyncError
import com.example.dragonballwiki.core.AsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : ViewModel() {


    private val _uiSate = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Start)
    val uiState = _uiSate.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(50000),
        CharacterDetailUiState.Start
    )
    fun getCharacterDetail(id: String) {
        viewModelScope.launch {
            getCharacterDetailUseCase(id).collect {
                _uiSate.value = when (it) {
                    is AsyncResult.Success -> {
                        CharacterDetailUiState.Success(
                            it.data
                        )
                    }

                    is AsyncResult.Error -> {
                        when (it.error) {
                            AsyncError.ConnectionError -> CharacterDetailUiState.Error("error en la conexíon")
                            is AsyncError.CustomError -> CharacterDetailUiState.Error("error en la conexíon")
                            AsyncError.DataParseError -> CharacterDetailUiState.Error("error en la conexíon")
                            AsyncError.EmptyResponseError -> CharacterDetailUiState.Error("error en la conexíon")
                            is AsyncError.ServerError -> CharacterDetailUiState.Error("error en la conexíon")
                            AsyncError.TimeoutError -> CharacterDetailUiState.Error("error en la conexíon")
                            is AsyncError.UnknownError -> CharacterDetailUiState.Error("error en la conexíon")
                        }
                    }

                    is AsyncResult.Loading -> {
                        CharacterDetailUiState.Loading
                    }
                }
            }
        }
    }
}