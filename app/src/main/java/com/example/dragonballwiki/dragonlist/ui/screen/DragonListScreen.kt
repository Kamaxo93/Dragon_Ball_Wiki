package com.example.dragonballwiki.dragonlist.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import com.example.dragonballwiki.R
import com.example.dragonballwiki.dragonlist.ui.model.CharacterVO
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState
import com.example.dragonballwiki.dragonlist.ui.viewmodel.DragonListViewModel
import com.example.dragonballwiki.ui.theme.ProgressIndicatorLogin

@Composable
fun DragonListScreen(dragonListViewModel: DragonListViewModel, onClickElement: (String) -> Unit, onClickErrorList: () -> Unit) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current

    val uiState by produceState<CharacterUiState>(
        initialValue = CharacterUiState.Loading,
        key1 = lifecycle,
        key2 = dragonListViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            dragonListViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is CharacterUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize().clickable { dragonListViewModel.uiState.replayCache }) {
                Text(
                    text = "La lista está vacia",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp).align(Alignment.Center)
                )
            }
        }

        is CharacterUiState.Loading -> {
            Box(
                modifier = Modifier,
            ) {
                LoginBall(modifier = Modifier.align(Alignment.Center))
            }
        }

        is CharacterUiState.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff272B33)),
            ) {
                DragonBallList(
                    characters = (uiState as CharacterUiState.Success).charactersVO,
                    onClickElement = {
                        onClickElement(
                            it
                        )
                    })
            }
        }

        is CharacterUiState.ErrorGeneric -> {
            Log.i("manolete", "DragonListScreen: ${(uiState as CharacterUiState.ErrorGeneric)}")
        }
    }
}

@Composable
fun DragonBallList(characters: CharactersVO, onClickElement: (String) -> Unit) {
    LazyColumn {
        items(characters.characterList, key = { it.id }) {
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
            .clickable { onClickElement(character.name) }) {
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
fun TextOtherData(title: String, data: String) {
    Column {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )
        Text(
            text = data,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFFBC02D),
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )
    }
}

@Composable
fun TextBreedAndGenreCharacter(breed: String, genre: String) {
    Text(
        text = "$breed - $genre",
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFFFBC02D),
        modifier = Modifier.padding(start = 16.dp, top = 8.dp)
    )
}

@Composable
fun NameCharacter(nameCharacter: String, modifier: Modifier = Modifier) {
    Text(
        text = nameCharacter,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        color = Color.White,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun ImageCharacter(urlImage: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = urlImage,
        contentDescription = null,
        modifier = modifier
    )
}


@Composable
fun LoginBall(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier.size(150.dp), color = ProgressIndicatorLogin)
    Image(
        painter = painterResource(id = R.drawable.dragon_four_start),
        contentDescription = "",
        modifier.size(280.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRecycler() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta),
    ) {
        DragonBallList(
            CharactersVO(
                listOf(
                    CharacterVO(
                        affiliation = "Sultan",
                        description = "Genise",
                        gender = "Rasheeda",
                        id = 7065,
                        image = "https://res.cloudinary.com/dgtgbyo76/image/upload/v1699139274/hcxz5pcptdevfud5mko0.webp",
                        ki = "Ernst",
                        maxKi = "Shana",
                        name = "Krystie",
                        race = "Yusuf"
                    )
                )
            )
        ) {}
    }
}