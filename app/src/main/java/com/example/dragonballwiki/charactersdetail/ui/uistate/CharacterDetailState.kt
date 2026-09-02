package com.example.dragonballwiki.charactersdetail.ui.uistate

import androidx.compose.runtime.Immutable
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.core.ui.CacheInfoVO
import com.example.dragonballwiki.core.ui.LoadingState
import com.example.dragonballwiki.core.ui.UiText

/**
 * UI State for the Character Detail screen.
 * Represents all the data needed to render a character's details.
 *
 * @property characterDetail Full character information with transformations
 * @property loadingState Current loading state (Idle, Loading, Refreshing)
 * @property error Error message to display, if any
 * @property cacheInfo Information about cache status, if data is from cache
 */
@Immutable
data class CharacterDetailState(
    val characterDetail: CharacterDetailVO? = null,
    val loadingState: LoadingState = LoadingState.Idle,
    val error: UiText? = null,
    val cacheInfo: CacheInfoVO? = null
)