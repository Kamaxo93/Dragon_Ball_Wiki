package com.example.dragonballwiki.dragonlist.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dragonballwiki.R
import com.example.dragonballwiki.dragonlist.ui.compose.ImageCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.NameCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.TextBreedAndGenreCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.TextOtherData
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
import com.example.dragonballwiki.dragonlist.ui.uistate.DragonListState
import com.example.dragonballwiki.dragonlist.ui.viewmodel.DragonListViewModel
import kotlinx.coroutines.launch

@Composable
fun DragonListScreen(
    dragonListViewModel: DragonListViewModel = hiltViewModel(),
    onClickElement: (String) -> Unit
) {
    val state = dragonListViewModel.state
    var searchCharacter by rememberSaveable {
        mutableStateOf("")
    }
    val isSearchCharacters by rememberSaveable {
        mutableStateOf(false)
    }
    when {
        state.error != null -> {
            CharactersContainerError {
                dragonListViewModel.reloadCharacterData()
            }
        }

        state.loading -> {
            LoginBall(stringResource(R.string.dragon_list_label_loading))
        }

        state.dragonListState != null -> {
            CharactersContainer(
                searchCharacter,
                dragonListViewModel,
                isSearchCharacters,
                state,
                onClickElement
            ) {
                searchCharacter = it
            }
        }
    }
}

@Composable
private fun CharactersContainerError(
    onReloadClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.character_list_empty),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 32.sp,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        Button(modifier = Modifier.padding(16.dp).background(MaterialTheme.colorScheme.primary), onClick = {
            onReloadClicked()
        }) {
            Text(
                text = stringResource(R.string.dragon_list_label_error),
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CharactersContainer(
    searchCharacter: String,
    dragonListViewModel: DragonListViewModel,
    isSearchCharacters: Boolean,
    state: DragonListState,
    onClickElement: (String) -> Unit,
    searchCharacterChange: (String) -> Unit
) {
    var isSearchCharacters1 = isSearchCharacters
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        SearchBar(
            query = searchCharacter,
            placeholder = {
                Text(text = stringResource(R.string.search_character))
            },
            onQueryChange = {
                searchCharacterChange(it)
                dragonListViewModel.searchCharacter(nameCharacter = it)
                isSearchCharacters1 = true
            },
            onSearch = {
                dragonListViewModel.searchCharacter(nameCharacter = it)
                isSearchCharacters1 = true
            },
            active = true,
            onActiveChange = {},
            shadowElevation = 8.dp
        ) {
            DragonBallList(
                characters = state.dragonListState,
                isSearchCharacters = isSearchCharacters1,
                onClickElement = {
                    onClickElement(
                        it
                    )
                    isSearchCharacters1 = false
                })
        }
    }
}

@Composable
fun DragonBallList(
    characters: List<CharacterVO>?,
    isSearchCharacters: Boolean,
    onClickElement: (String) -> Unit
) {
    val state = rememberLazyListState(0)
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(state = state) {
        if (isSearchCharacters) {
            coroutineScope.launch {
                state.scrollToItem(0)
            }
        }
        items(characters ?: listOf(), key = { it.id }) {
            ItemCharacter(character = it) {
                onClickElement(it)
            }
        }
    }
}

@Composable
fun ItemCharacter(character: CharacterVO, onClickElement: (String) -> Unit) {
    Card(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .clickable { onClickElement(character.id.toString()) }) {
        ImageCharacter(
            urlImage = character.image,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
                .size(250.dp)

        )
        InfoCharacter(
            character,
            Modifier
        )
    }
}

@Composable
fun InfoCharacter(character: CharacterVO, modifier: Modifier) {
    Column(modifier.fillMaxWidth()) {
        NameCharacter(character.name, modifier = Modifier.align(Alignment.CenterHorizontally))
        TextBreedAndGenreCharacter(genre = character.gender, breed = character.race)
        TextOtherData(stringResource(R.string.base_ki), character.ki)
        TextOtherData(stringResource(R.string.maxi_ki), character.maxKi)
        TextOtherData(stringResource(R.string.affiliation), character.affiliation)
    }
}

@Composable
fun LoginBall(message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center),
            )
            Image(
                painter = painterResource(id = R.drawable.dragon_four_start),
                contentDescription = stringResource(id = R.string.content_description_ball_loading),
                Modifier
                    .align(Alignment.Center)
                    .size(280.dp)
            )
        }
        Text(
            text = message,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRecycler() {
    CharactersContainerError() {}
}