package com.example.dragonballwiki.dragonlist.ui.screen

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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import com.example.dragonballwiki.dragonlist.ui.viewmodel.DragonListViewModel
import com.example.dragonballwiki.ui.theme.InferiorBackgroundColor
import com.example.dragonballwiki.ui.theme.ProgressIndicatorLogin
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState
import com.example.dragonballwiki.dragonlist.ui.uistate.DragonListState
import okhttp3.internal.notify

@Composable
fun DragonListScreen(
    dragonListViewModel: DragonListViewModel = hiltViewModel(),
    onClickElement: (String) -> Unit
) {
    val state = dragonListViewModel.state

    when  {
        state.error.isNotEmpty() -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable { dragonListViewModel.reloadList() }) {
                Text(
                    text = "La lista está vacia",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Center)
                )
            }
        }

        state.loading -> {
            LoginBall()
        }

        state.dragonListState?.characterList?.isNotEmpty() == true -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff272B33)),
            ) {
                DragonBallList(
                    characters = state.dragonListState,
                    onClickElement = {
                        onClickElement(
                            it
                        )
                    })
            }
        }
    }
}

@Composable
fun DragonBallList(characters: CharactersVO?, onClickElement: (String) -> Unit) {
    LazyColumn {
        items(characters?.characterList.orEmpty(), key = { it.id }) {
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
            contentDescription = "",
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
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Magenta),
//    ) {
//        DragonBallList(
//            CharactersVO(
//                listOf(
//                    CharacterVO(
//                        affiliation = "Sultan",
//                        description = "Genise",
//                        gender = "Rasheeda",
//                        id = 7065,
//                        image = "https://res.cloudinary.com/dgtgbyo76/image/upload/v1699139274/hcxz5pcptdevfud5mko0.webp",
//                        ki = "Ernst",
//                        maxKi = "Shana",
//                        name = "Krystie",
//                        race = "Yusuf"
//                    )
//                )
//            )
//        ) {}
//    }
}