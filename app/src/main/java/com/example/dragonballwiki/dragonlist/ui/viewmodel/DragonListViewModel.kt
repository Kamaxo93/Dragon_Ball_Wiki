package com.example.dragonballwiki.dragonlist.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.R
import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.dragonlist.domain.usecase.AddCharactersLocalDataBaseUseCase
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
import com.example.dragonballwiki.dragonlist.ui.model.toVO
import com.example.dragonballwiki.dragonlist.ui.uistate.DragonListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DragonListViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val addCharactersLocalDataBaseUseCase: AddCharactersLocalDataBaseUseCase
) : ViewModel() {
    var state by mutableStateOf(DragonListState())
        private set

    private var characterList = listOf<CharacterVO>()

    init {
        dataState()
    }

    private fun dataState() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                state = state.copy(error = null, dragonListState = null, loading = true)
            }
            getCharacterListUseCase().collect { Characters ->
                if (Characters.isEmpty()) {
                    addCharactersLocalDataBaseUseCase().collect {
                        when (it) {
                            is AsyncResult.Error -> state = state.copy(
                                error = R.string.text_error_service,
                                dragonListState = null,
                                loading = false
                            )

                            is AsyncResult.Loading -> state =
                                state.copy(error = null, dragonListState = null, loading = true)

                            is AsyncResult.Success -> {}
                        }
                    }

                } else {
                    characterList = Characters.toVO()
                    state = state.copy(
                        dragonListState = characterList,
                        loading = false,
                        error = null
                    )
                }
            }
        }
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

    fun reloadCharacterData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (characterList.isEmpty()) {
                addCharactersLocalDataBaseUseCase().collect {
                    when (it) {
                        is AsyncResult.Error -> state = state.copy(
                            error = R.string.text_error_service,
                            dragonListState = null,
                            loading = false
                        )

                        is AsyncResult.Loading -> state =
                            state.copy(error = null, dragonListState = null, loading = true)

                        is AsyncResult.Success -> {
                            /** la lista al ser un flow, room la actualiza el state si observa algún cambio */
                            //no-op
                        }
                    }
                }
            }
        }
    }
}
