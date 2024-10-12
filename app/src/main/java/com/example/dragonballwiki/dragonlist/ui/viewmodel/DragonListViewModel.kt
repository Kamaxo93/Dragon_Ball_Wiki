package com.example.dragonballwiki.dragonlist.ui.viewmodel

import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.core.AsyncError
import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.core.isTrue
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
import com.example.dragonballwiki.dragonlist.ui.uistate.DragonListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DragonListViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase
) : ViewModel() {

    var state by mutableStateOf(DragonListState())
        private set

    private var isInitialized = false

    private var characterList = listOf<CharacterVO>()

    @MainThread
    fun initializeDataState() {
        if (isInitialized) return
        isInitialized = true
        dataState()
    }

    private fun dataState() {
        viewModelScope.launch {
            getCharacterListUseCase().collect {
                when (it) {
                    is AsyncResult.Success -> {
                        characterList = it.data.characterList.toMutableList()
                        state = state.copy(
                            dragonListState = characterList,
                            loading = false,
                            error = null
                        )
                    }

                    is AsyncResult.Error -> {
                        when (it.error) {
                            AsyncError.ConnectionError,
                            is AsyncError.CustomError,
                            AsyncError.DataParseError,
                            AsyncError.EmptyResponseError,
                            is AsyncError.ServerError,
                            AsyncError.TimeoutError,
                            is AsyncError.UnknownError -> state = state.copy(
                                error = "error en la conexíon",
                                dragonListState = listOf(),
                                loading = false
                            )
                        }
                    }

                    is AsyncResult.Loading -> {
                        state = state.copy(error = null, dragonListState = listOf(), loading = true)
                    }
                }
            }

        }
    }

    fun reloadList() {
        dataState()
    }

    fun searchCharacter(nameCharacter: String) {
        state = state.copy(dragonListState =
        if (nameCharacter.isNotBlank()) {
            characterList.filter {
                it.name.contains(
                    nameCharacter,
                    true
                )
            }
        } else {
            characterList
        }, loading = false, error = null)
    }
}
