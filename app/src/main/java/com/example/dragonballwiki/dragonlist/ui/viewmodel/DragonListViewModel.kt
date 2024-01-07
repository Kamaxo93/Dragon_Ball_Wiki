package com.example.dragonballwiki.dragonlist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.core.NetWorkError
import com.example.dragonballwiki.core.Resource
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject

@HiltViewModel
class DragonListViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase

) : ViewModel() {

    val uiState = getCharacterListUseCase().map {
        when (it) {
            is Resource.Success -> Success(
                it.data ?: CharactersVO(
                    characterList = listOf()
                )
            )

            is Resource.Error -> {
                when (it) {
                    is NetWorkError -> CharacterUiState.Error(it.message)
                    else -> {
                        CharacterUiState.Error(it.message)
                    }
                }
            }

            is Resource.ErrorsResponse -> CharacterUiState.Error(it.errorResponse?.message)
        }
    }.catch {
        CharacterUiState.ErrorGeneric(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CharacterUiState.Loading)

    fun reloadList() {
        viewModelScope.launch(Dispatchers.IO) {
            getCharacterListUseCase().collect()
        }
    }
}
