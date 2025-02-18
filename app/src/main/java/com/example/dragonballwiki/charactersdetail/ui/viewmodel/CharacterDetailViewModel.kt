package com.example.dragonballwiki.charactersdetail.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCase
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetTransformations
import com.example.dragonballwiki.charactersdetail.ui.uistate.CharacterDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val getTransformations: GetTransformations
) : ViewModel() {

    var state by mutableStateOf(CharacterDetailState())
        private set

    private var isInitialized = false

    fun initializeDataState(id: String) {
        if (isInitialized) return
        isInitialized = true
        getCharacterDetail(id)
    }

    private fun getCharacterDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                state = state.copy(characterDetail = null, loading = true, error = null)

            }
            getCharacterDetailUseCase(id).collect {
                if (it.transformations.isEmpty()) {
                    getTransformations(it.id).collect { transformations ->
                        state = state.copy(
                            characterDetail = it.copy(transformations = transformations),
                            loading = false,
                            error = null
                        )
                    }
                } else {
                    state = state.copy(characterDetail = it, loading = false, error = null)
                }
            }
        }
    }
}