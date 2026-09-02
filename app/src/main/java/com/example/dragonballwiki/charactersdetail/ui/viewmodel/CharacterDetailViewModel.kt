package com.example.dragonballwiki.charactersdetail.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.R
import com.example.dragonballwiki.charactersdetail.data.remote.mapper.toVO
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailErrorUseCase
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCase
import com.example.dragonballwiki.charactersdetail.ui.uistate.CharacterDetailState
import com.example.dragonballwiki.core.Constant.CHARACTER_ID
import com.example.dragonballwiki.core.async.AsyncError
import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.core.ui.CacheInfoVO
import com.example.dragonballwiki.core.ui.CacheSeverity
import com.example.dragonballwiki.core.ui.LoadingState
import com.example.dragonballwiki.core.ui.UiText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

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
 * - Cálculo de CacheInfo movido a ViewModel (separación de responsabilidades)
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
                        val cacheInfo = result.data.lastUpdated?.let { calculateCacheInfo(it) }
                        state = state.copy(
                            characterDetail = result.data,
                            loadingState = LoadingState.Idle,
                            error = null,
                            cacheInfo = cacheInfo
                        )
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
                                val characterVO = localCharacter.toVO()
                                val cacheInfo = characterVO.lastUpdated?.let { calculateCacheInfo(it) }
                                
                                state = state.copy(
                                    characterDetail = characterVO,
                                    loadingState = LoadingState.Idle,
                                    error = null,
                                    cacheInfo = cacheInfo
                                )
                            }
                        }
                    }

                    is AsyncResult.Loading -> {
                        state = state.copy(
                            characterDetail = null,
                            loadingState = LoadingState.Loading,
                            error = null
                        )
                    }
                }
            }
        }
    }

    /**
     * Calculates cache information for displaying cache status.
     * Moved from UI layer to ViewModel to separate business logic from presentation.
     *
     * @param lastUpdated Timestamp in milliseconds when data was last updated
     * @return CacheInfoVO with pre-calculated emoji, message, and severity
     */
    private fun calculateCacheInfo(lastUpdated: Long): CacheInfoVO {
        val currentTime = System.currentTimeMillis()
        val diffInMillis = currentTime - lastUpdated
        val daysOld = TimeUnit.MILLISECONDS.toDays(diffInMillis)

        return when {
            daysOld == 0L -> CacheInfoVO(
                lastUpdated = lastUpdated,
                daysOld = daysOld,
                emoji = "📡",
                message = UiText.StringResource(R.string.cache_updated_today),
                severity = CacheSeverity.FRESH
            )
            daysOld < 5 -> CacheInfoVO(
                lastUpdated = lastUpdated,
                daysOld = daysOld,
                emoji = "✅",
                message = UiText.DynamicString(
                    if (daysOld == 1L) "Actualizado hace $daysOld día"
                    else "Actualizado hace $daysOld días"
                ),
                severity = CacheSeverity.RECENT
            )
            daysOld < 10 -> CacheInfoVO(
                lastUpdated = lastUpdated,
                daysOld = daysOld,
                emoji = "⚠️",
                message = UiText.DynamicString("Actualizado hace $daysOld días"),
                severity = CacheSeverity.OLD
            )
            else -> CacheInfoVO(
                lastUpdated = lastUpdated,
                daysOld = daysOld,
                emoji = "⚠️",
                message = UiText.DynamicString("Los datos pueden estar desactualizados ($daysOld días)"),
                severity = CacheSeverity.STALE
            )
        }
    }
}