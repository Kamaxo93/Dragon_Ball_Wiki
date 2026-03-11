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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para el detalle de personajes.
 * 
 * Migrado de Hilt a Koin:
 * - Eliminado @HiltViewModel
 * - Eliminado @Inject y @IoDispatcher
 * - Constructor normal - Koin inyecta automáticamente
 * 
 * Mejoras aplicadas:
 * - Inyección de CoroutineDispatcher para facilitar testing
 * - Eliminado nested Flow collectors para prevenir memory leaks
 * - Uso de Main dispatcher para actualizaciones de estado UI (thread safety)
 * - withContext(ioDispatcher) solo para operaciones de BD local
 */
class CharacterDetailViewModel(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val getCharacterDetailErrorUseCase: GetCharacterDetailErrorUseCase,
    private val ioDispatcher: CoroutineDispatcher,
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
            getCharacterDetailUseCase(id).collect { result ->
                when (result) {
                    is AsyncResult.Success -> {
                        state = state.copy(characterDetail = result.data, loading = false, error = null)
                    }

                    is AsyncResult.Error -> {
                        // Fallback: try to get character from local list database
                        when (result.error) {
                            AsyncError.ConnectionError,
                            is AsyncError.CustomError,
                            AsyncError.DataParseError,
                            AsyncError.EmptyResponseError,
                            is AsyncError.ServerError,
                            AsyncError.TimeoutError,
                            is AsyncError.UnknownError -> {
                                // Use withContext for IO operation only
                                val localCharacter = withContext(ioDispatcher) {
                                    getCharacterDetailErrorUseCase(id).first()
                                }
                                state = state.copy(
                                    characterDetail = localCharacter.toVO(),
                                    loading = false,
                                    error = null
                                )
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