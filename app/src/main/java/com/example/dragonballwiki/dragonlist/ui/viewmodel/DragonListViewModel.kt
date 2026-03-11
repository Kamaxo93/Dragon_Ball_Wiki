package com.example.dragonballwiki.dragonlist.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.R
import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.core.di.IoDispatcher
import com.example.dragonballwiki.dragonlist.domain.usecase.AddCharactersLocalDataBaseUseCase
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
import com.example.dragonballwiki.dragonlist.ui.model.toVO
import com.example.dragonballwiki.dragonlist.ui.uistate.DragonListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la lista de personajes de Dragon Ball.
 * 
 * Mejoras aplicadas:
 * - Inyección de CoroutineDispatcher para facilitar testing
 * - Eliminado nested Flow collectors para prevenir memory leaks
 * - Eliminado withContext innecesario (state updates son main-safe)
 */
@HiltViewModel
class DragonListViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val addCharactersLocalDataBaseUseCase: AddCharactersLocalDataBaseUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    var state by mutableStateOf(DragonListState())
        private set

    private var characterList = listOf<CharacterVO>()

    init {
        dataState()
    }

    private fun dataState() {
        viewModelScope.launch(ioDispatcher) {
            state = state.copy(error = null, dragonListState = null, loading = true)
            
            getCharacterListUseCase().collect { characters ->
                if (characters.isEmpty()) {
                    // Fetch from remote if local DB is empty
                    val result = addCharactersLocalDataBaseUseCase().first()
                    when (result) {
                        is AsyncResult.Error -> state = state.copy(
                            error = R.string.text_error_service,
                            dragonListState = null,
                            loading = false
                        )

                        is AsyncResult.Loading -> state =
                            state.copy(error = null, dragonListState = null, loading = true)

                        is AsyncResult.Success -> {
                            // Flow will auto-update when Room data changes
                            // no-op
                        }
                    }
                } else {
                    characterList = characters.toVO()
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
        viewModelScope.launch(ioDispatcher) {
            if (characterList.isEmpty()) {
                val result = addCharactersLocalDataBaseUseCase().first()
                when (result) {
                    is AsyncResult.Error -> state = state.copy(
                        error = R.string.text_error_service,
                        dragonListState = null,
                        loading = false
                    )

                    is AsyncResult.Loading -> state =
                        state.copy(error = null, dragonListState = null, loading = true)

                    is AsyncResult.Success -> {
                        // La lista al ser un flow, room la actualiza el state si observa algún cambio
                        //no-op
                    }
                }
            }
        }
    }
}