package com.example.dragonballwiki.dragonlist.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dragonballwiki.R
import com.example.dragonballwiki.core.ui.LoadingState
import com.example.dragonballwiki.dragonlist.ui.compose.ImageCharacter
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
import com.example.dragonballwiki.dragonlist.ui.uistate.DragonListState
import com.example.dragonballwiki.dragonlist.ui.viewmodel.DragonListViewModel
import com.example.dragonballwiki.ui.components.EmptyStateCompose
import com.example.dragonballwiki.ui.components.ErrorStateCompose
import com.example.dragonballwiki.ui.theme.ImageSizes
import com.example.dragonballwiki.ui.theme.Spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun DragonListScreen(
    dragonListViewModel: DragonListViewModel = koinViewModel(),
    onClickElement: (String) -> Unit
) {
    val state = dragonListViewModel.state
    var searchCharacter by rememberSaveable {
        mutableStateOf("")
    }
    var isSearchActive by rememberSaveable {
        mutableStateOf(false)
    }

    when {
        state.error != null -> {
            ErrorStateCompose(
                title = state.error.asString(),
                description = stringResource(R.string.dragon_list_label_error),
                onRetryClick = { dragonListViewModel.reloadCharacterData() }
            )
        }

        state.loadingState == LoadingState.Loading -> {
            LoginBall(stringResource(R.string.dragon_list_label_loading))
        }

        else -> {
            CharactersContainer(
                searchCharacter = searchCharacter,
                dragonListViewModel = dragonListViewModel,
                isSearchActive = isSearchActive,
                state = state,
                onClickElement = onClickElement,
                onSearchActiveChange = { isSearchActive = it },
                searchCharacterChange = { searchCharacter = it }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CharactersContainer(
    searchCharacter: String,
    dragonListViewModel: DragonListViewModel,
    isSearchActive: Boolean,
    state: DragonListState,
    onClickElement: (String) -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
    searchCharacterChange: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { dragonListViewModel.reloadCharacterData() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(R.string.content_description_refresh),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            SearchBar(
                query = searchCharacter,
                placeholder = {
                    Text(text = stringResource(R.string.search_character))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.content_description_search)
                    )
                },
                onQueryChange = {
                    searchCharacterChange(it)
                    dragonListViewModel.searchCharacter(nameCharacter = it)
                },
                onSearch = {
                    dragonListViewModel.searchCharacter(nameCharacter = it)
                    onSearchActiveChange(false)
                },
                active = isSearchActive,
                onActiveChange = { onSearchActiveChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = if (isSearchActive) 0.dp else Spacing.medium,
                        vertical = Spacing.small
                    )
            ) {
                DragonBallList(
                    characters = state.filteredCharacters,
                    searchCharacter = searchCharacter,
                    onClickElement = {
                        onClickElement(it)
                        onSearchActiveChange(false)
                    }
                )
            }

            if (!isSearchActive) {
                DragonBallList(
                    characters = state.filteredCharacters,
                    searchCharacter = searchCharacter,
                    onClickElement = onClickElement
                )
            }
        }
    }
}

@Composable
fun DragonBallList(
    characters: List<CharacterVO>,
    searchCharacter: String,
    onClickElement: (String) -> Unit
) {
    if (characters.isEmpty() && searchCharacter.isNotEmpty()) {
        EmptyStateCompose(
            title = stringResource(R.string.no_characters_found),
            description = stringResource(R.string.search_hint)
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(characters, key = { it.id }) {
                ItemCharacter(character = it) {
                    onClickElement(it)
                }
            }
        }
    }
}

@Composable
fun ItemCharacter(character: CharacterVO, onClickElement: (String) -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.medium, vertical = Spacing.small)
            .clickable { onClickElement(character.id.toString()) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageCharacter(
                urlImage = character.image,
                modifier = Modifier
                    .size(ImageSizes.characterListThumbnail)
                    .padding(Spacing.small)
            )
            
            InfoCharacter(
                character = character,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = Spacing.small)
            )
        }
    }
}

@Composable
fun InfoCharacter(character: CharacterVO, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = character.name,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Text(
            text = "${character.race} • ${character.gender}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = Spacing.extraSmall)
        )
        
        if (character.ki.isNotEmpty() && character.ki != stringResource(R.string.value_unknown).lowercase()) {
            Text(
                text = stringResource(R.string.stat_ki, character.ki),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = Spacing.extraSmall)
            )
        }
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
                    .size(ImageSizes.loadingIndicator)
                    .align(Alignment.Center),
            )
            Image(
                painter = painterResource(id = R.drawable.dragon_four_start),
                contentDescription = stringResource(id = R.string.content_description_ball_loading),
                Modifier
                    .align(Alignment.Center)
                    .size(ImageSizes.loadingDragonBall)
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