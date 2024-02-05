package com.example.dragonballwiki.dragonlist.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.core.AsyncError
import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.dragonlist.domain.usecase.AddCharactersLocalDataBaseUseCase
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
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
            getCharacterListUseCase().collect {
                try {
                    if (it.characterList.isEmpty()) {
                        addCharactersLocalDataBaseUseCase()

                    } else {
                        characterList = it.characterList
                        state = state.copy(
                            dragonListState = characterList,
                            loading = false,
                            error = null
                        )
                    }
                } catch (e: Exception) {
                    state = state.copy(
                        error = "error en la conexíon",
                        dragonListState = null,
                        loading = false
                    )
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

    fun add() {
        viewModelScope.launch(Dispatchers.IO) {
            if (characterList.isEmpty()) {
                addCharactersLocalDataBaseUseCase()
                dataState()
            }
        }
    }
}
