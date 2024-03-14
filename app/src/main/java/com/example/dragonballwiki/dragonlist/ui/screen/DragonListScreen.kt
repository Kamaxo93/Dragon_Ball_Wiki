package com.example.dragonballwiki.dragonlist.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dragonballwiki.R
import com.example.dragonballwiki.dragonlist.ui.compose.ImageCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.NameCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.TextBreedAndGenreCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.TextOtherData
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
import com.example.dragonballwiki.dragonlist.ui.uistate.DragonListState
import com.example.dragonballwiki.dragonlist.ui.viewmodel.DragonListViewModel
import com.example.dragonballwiki.ui.theme.InferiorBackgroundColor
import com.example.dragonballwiki.ui.theme.ProgressIndicatorLogin
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun DragonListScreen(
    dragonListViewModel: DragonListViewModel = koinViewModel(),
    onClickElement: (String) -> Unit
) {
    val state = dragonListViewModel.state
    val context: Context = LocalContext.current
    var searchCharacter by rememberSaveable {
        mutableStateOf("")
    }
    var isSearchCharacters by rememberSaveable {
        mutableStateOf(false)
    }
    when {
        state.error != null -> {
            CharactersContainerError(dragonListViewModel, context)
            Toast.makeText(context, context.getText(state.error), Toast.LENGTH_SHORT).show()
        }

        state.loading -> {
            LoginBall()
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
    dragonListViewModel: DragonListViewModel,
    context: Context
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable { dragonListViewModel.reloadCharacterData() }) {
        Text(
            text = context.getString(R.string.character_list_empty),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 32.sp,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Center)
        )
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
            .fillMaxSize()
            .background(Color(0xff272B33)),
    ) {
        SearchBar(
            query = searchCharacter,
            placeholder = {
                Text(text = "Buscar personaje")
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
            onActiveChange = {

            },
            modifier = Modifier.padding(horizontal = 8.dp)
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
                .background(Color.Yellow)
        )
    }
}

@Composable
fun InfoCharacter(character: CharacterVO, modifier: Modifier) {
    Column(modifier.background(Color(0xFF3C3E44))) {
        NameCharacter(character.name, modifier = Modifier.align(Alignment.CenterHorizontally))
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFF3C3E44))
            ) {
                TextBreedAndGenreCharacter(genre = character.gender, breed = character.race)
                TextOtherData("Base KI", character.ki)
                TextOtherData("Total KI", character.maxKi)
                TextOtherData("Afiliación", character.affiliation)
            }
        }
    }
}

@Composable
fun LoginBall() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InferiorBackgroundColor),
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.Center),
            color = ProgressIndicatorLogin
        )
        Image(
            painter = painterResource(id = R.drawable.dragon_four_start),
            contentDescription = "imagen del personaje",
            Modifier
                .align(Alignment.Center)
                .size(280.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRecycler() {
    LoginBall()
}