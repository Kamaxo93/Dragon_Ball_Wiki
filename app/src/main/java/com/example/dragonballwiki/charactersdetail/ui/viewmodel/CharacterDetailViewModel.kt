package com.example.dragonballwiki.charactersdetail.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.charactersdetail.data.remote.mapper.toVO
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailErrorUseCase
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCase
import com.example.dragonballwiki.charactersdetail.ui.uistate.CharacterDetailState
import com.example.dragonballwiki.core.Constant.CHARACTER_ID
import com.example.dragonballwiki.core.async.AsyncError
import com.example.dragonballwiki.core.async.AsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val getCharacterDetailErrorUseCase: GetCharacterDetailErrorUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(CharacterDetailState())
        private set

    val id = savedStateHandle.get<String>(CHARACTER_ID)

    init {
        getCharacterDetail(id.orEmpty())
    }

    private fun getCharacterDetail(id: String) {
        viewModelScope.launch {
            getCharacterDetailUseCase(id).collect {
                when (it) {
                    is AsyncResult.Success -> {
                        state = state.copy(characterDetail = it.data, loading = false, error = null)
                    }

                    is AsyncResult.Error -> {
                        when (it.error) {
                            AsyncError.ConnectionError,
                            is AsyncError.CustomError,
                            AsyncError.DataParseError,
                            AsyncError.EmptyResponseError,
                            is AsyncError.ServerError,
                            AsyncError.TimeoutError,
                            is AsyncError.UnknownError -> {
                                getCharacterDetailErrorUseCase(id).collect {
                                    state = state.copy(
                                        characterDetail = it.toVO(),
                                        loading = false,
                                        error = null
                                    )
                                }
                            }
                        }
                    }

                    is AsyncResult.Loading -> {
                        state = state.copy(characterDetail = null, loading = true, error = null)
                    }
                }
            }
        }
    }
}