package com.example.dragonballwiki.dragonlist.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballwiki.core.AsyncError
import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import com.example.dragonballwiki.dragonlist.ui.uistate.DragonListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DragonListViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase
) : ViewModel() {

    var state by mutableStateOf(DragonListState())
        private set

    init {
        dataState()
    }

    private fun dataState() {
        viewModelScope.launch {
            getCharacterListUseCase().collectLatest {
                when (it) {
                    is AsyncResult.Success -> {
                        state = state.copy(dragonListState = it.data, loading = false)
                    }

                    is AsyncResult.Error -> {
                        when (it.error) {
                            AsyncError.ConnectionError,
                            is AsyncError.CustomError,
                            AsyncError.DataParseError,
                            AsyncError.EmptyResponseError,
                            is AsyncError.ServerError,
                            AsyncError.TimeoutError,
                            is AsyncError.UnknownError -> state = state.copy(error ="error en la conexíon", dragonListState = CharactersVO(listOf()), loading = false)
                        }
                    }

                    is AsyncResult.Loading -> {
                        state = state.copy(error = "", dragonListState = CharactersVO(listOf()), loading = true)
                    }
                }
            }

        }
    }

    fun reloadList() {
        dataState()
    }
}
