package com.example.dragonballwiki.dragonlist.ui.uistate

import androidx.compose.runtime.Immutable
import com.example.dragonballwiki.core.ui.LoadingState
import com.example.dragonballwiki.core.ui.UiText
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO

/**
 * UI State for the Dragon List screen.
 * Represents all the data needed to render the character list.
 *
 * @property characters Full list of characters from the repository
 * @property filteredCharacters Filtered list based on search query
 * @property loadingState Current loading state (Idle, Loading, Refreshing)
 * @property error Error message to display, if any
 * @property searchQuery Current search query text
 */
@Immutable
data class DragonListState(
    val characters: List<CharacterVO> = emptyList(),
    val filteredCharacters: List<CharacterVO> = emptyList(),
    val loadingState: LoadingState = LoadingState.Idle,
    val error: UiText? = null,
    val searchQuery: String = ""
)