package com.example.dragonballwiki.dragonlist.ui.viewmodel

import android.util.Log
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel para la lista de personajes de Dragon Ball.
 * 
 * Migrado de Hilt a Koin:
 * - Eliminado @HiltViewModel
 * - Eliminado @Inject y @IoDispatcher
 * - Constructor normal - Koin inyecta automáticamente
 * 
 * Mejoras aplicadas:
 * - Eliminado nested Flow collectors para prevenir memory leaks
 * - Eliminado withContext innecesario (state updates son main-safe)
 * - Repository maneja threading con flowOn(Dispatchers.IO)
 * - ViewModel lanza coroutines en Main (viewModelScope default)
 */
class DragonListViewModel(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val addCharactersLocalDataBaseUseCase: AddCharactersLocalDataBaseUseCase
) : ViewModel() {
    
    companion object {
        private const val TAG = "DragonList-VM"
    }
    
    var state by mutableStateOf(DragonListState())
        private set

    init {
        Log.d(TAG, "🎬 ViewModel initialized - Starting dataState()")
        dataState()
    }

    private fun dataState() {
        Log.d(TAG, "📡 dataState() called - Launching coroutine on Main (viewModelScope default)")
        viewModelScope.launch {
            Log.d(TAG, "🧵 Coroutine running on ${Thread.currentThread().name}")
            Log.d(TAG, "🔄 Setting initial Loading state")
            state = state.copy(
                error = null,
                characters = emptyList(),
                filteredCharacters = emptyList(),
                loadingState = com.example.dragonballwiki.core.ui.LoadingState.Loading
            )
            
            Log.d(TAG, "🔍 Calling getCharacterListUseCase() to observe local DB")
            getCharacterListUseCase().collect { characters ->
                Log.d(TAG, "📦 Flow emission received on ${Thread.currentThread().name} - Characters count: ${characters.size}")
                if (characters.isEmpty()) {
                    Log.w(TAG, "⚠️ Local DB is empty - Fetching from remote API")
                    // Fetch from remote if local DB is empty
                    // Use first { } predicate to skip Loading state and wait for Success/Error
                    val result = addCharactersLocalDataBaseUseCase().first { it !is AsyncResult.Loading }
                    Log.d(TAG, "🌐 Remote fetch completed - Result: ${result::class.simpleName}")
                    when (result) {
                        is AsyncResult.Error -> {
                            Log.e(TAG, "❌ Error fetching characters from API: ${result.error}")
                            state = state.copy(
                                error = com.example.dragonballwiki.core.ui.UiText.StringResource(
                                    R.string.text_error_service
                                ),
                                characters = emptyList(),
                                filteredCharacters = emptyList(),
                                loadingState = com.example.dragonballwiki.core.ui.LoadingState.Idle
                            )
                        }

                        is AsyncResult.Loading -> {
                            Log.d(TAG, "⏳ Still loading from API...")
                            state = state.copy(
                                error = null,
                                loadingState = com.example.dragonballwiki.core.ui.LoadingState.Loading
                            )
                        }

                        is AsyncResult.Success -> {
                            Log.i(TAG, "✅ Successfully fetched and saved to DB - Waiting for Room to emit...")
                            // Flow will auto-update when Room data changes
                            // no-op
                        }
                    }
                } else {
                    Log.i(TAG, "✅ Characters found in local DB - Updating UI state")
                    val characterVOList = characters.toVO()
                    state = state.copy(
                        characters = characterVOList,
                        filteredCharacters = if (state.searchQuery.isBlank()) {
                            characterVOList
                        } else {
                            characterVOList.filter { 
                                it.name.contains(state.searchQuery, ignoreCase = true) 
                            }
                        },
                        loadingState = com.example.dragonballwiki.core.ui.LoadingState.Idle,
                        error = null
                    )
                    Log.d(TAG, "🎉 UI State updated - Characters: ${characterVOList.size}, Filtered: ${state.filteredCharacters.size}")
                }
            }
        }
    }

    fun searchCharacter(nameCharacter: String) {
        val filteredList = if (nameCharacter.isNotBlank()) {
            state.characters.filter {
                it.name.contains(nameCharacter, ignoreCase = true)
            }
        } else {
            state.characters
        }
        
        state = state.copy(
            searchQuery = nameCharacter,
            filteredCharacters = filteredList,
            loadingState = com.example.dragonballwiki.core.ui.LoadingState.Idle,
            error = null
        )
    }

    fun reloadCharacterData() {
        Log.d(TAG, "🔄 reloadCharacterData() called - Refreshing data")
        viewModelScope.launch {
            Log.d(TAG, "🧵 Reload running on ${Thread.currentThread().name}")
            state = state.copy(loadingState = com.example.dragonballwiki.core.ui.LoadingState.Refreshing)
            
            Log.d(TAG, "🌐 Fetching fresh data from API")
            val result = addCharactersLocalDataBaseUseCase().first()
            Log.d(TAG, "📡 Reload result: ${result::class.simpleName}")
            when (result) {
                is AsyncResult.Error -> {
                    Log.e(TAG, "❌ Error reloading data: ${result.error}")
                    state = state.copy(
                        error = com.example.dragonballwiki.core.ui.UiText.StringResource(
                            R.string.text_error_service
                        ),
                        loadingState = com.example.dragonballwiki.core.ui.LoadingState.Idle
                    )
                }

                is AsyncResult.Loading -> {
                    Log.d(TAG, "⏳ Still loading during reload...")
                    state = state.copy(
                        error = null,
                        loadingState = com.example.dragonballwiki.core.ui.LoadingState.Refreshing
                    )
                }

                is AsyncResult.Success -> {
                    Log.i(TAG, "✅ Reload successful - Room will auto-update")
                    // Flow will auto-update when Room data changes
                    state = state.copy(
                        loadingState = com.example.dragonballwiki.core.ui.LoadingState.Idle
                    )
                }
            }
        }
    }
}