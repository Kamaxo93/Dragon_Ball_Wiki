package com.example.dragonballwiki.dragonlist.ui.viewmodel

import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.R
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
    private val getCharacterListUseCase: GetCharacterListUseCase, ) : ViewModel() {
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
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                state = state.copy(error = null, dragonListState = null, loading = true)
            }
            getCharacterListUseCase().collect {
                when (it) {
                    is Exception -> state = state.copy(
                        error = R.string.text_error_service,
                        dragonListState = null,
                        loading = false
                    )
                    else -> {
                        characterList = it.toVO()
                        state = state.copy(
                            error = null,
                            dragonListState = characterList,
                            loading = false)
                    }
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
        dataState()
    }
}
