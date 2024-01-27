package com.example.dragonballwiki.dragonlist.ui.uistate

import androidx.compose.runtime.Immutable
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO

@Immutable
data class DragonListState(
    val dragonListState: List<CharacterVO>? = null,
    val loading: Boolean = false,
    val error: String? = null
)