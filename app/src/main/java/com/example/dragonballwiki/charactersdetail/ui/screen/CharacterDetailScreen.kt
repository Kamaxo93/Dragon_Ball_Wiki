package com.example.dragonballwiki.charactersdetail.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.charactersdetail.ui.model.Transformation
import com.example.dragonballwiki.charactersdetail.ui.uistate.CharacterDetailUiState
import com.example.dragonballwiki.charactersdetail.ui.viewmodel.CharacterDetailViewModel
import com.example.dragonballwiki.dragonlist.ui.compose.ImageCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.KiTransformationCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.NameCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.TextBreedAndGenreCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.TextOtherData
import com.example.dragonballwiki.dragonlist.ui.screen.LoginBall
import com.example.dragonballwiki.ui.theme.ColumnBackgroundColor
import com.example.dragonballwiki.ui.theme.InferiorBackgroundColor
import com.example.dragonballwiki.ui.theme.SuperiorBackgroundColor


@Composable
fun CharacterDetailScreen(characterDetailViewModel: CharacterDetailViewModel) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<CharacterDetailUiState>(
        initialValue = CharacterDetailUiState.Start,
        key1 = lifecycle,
        key2 = characterDetailViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            characterDetailViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is CharacterDetailUiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
            {
                Text(
                    text = "No exixten datos",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Center)
                )
            }
        }

        is CharacterDetailUiState.Loading -> {
            LoginBall()
        }

        is CharacterDetailUiState.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff272B33)),
            ) {
                CharacterDetail(characterDetailVO = (uiState as CharacterDetailUiState.Success).character)
            }
        }

        is CharacterDetailUiState.Start -> {
            //no-op
        }
    }
}

@Composable
fun CharacterDetail(characterDetailVO: CharacterDetailVO) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = ColumnBackgroundColor)
    ) {
        Card(Modifier.padding(8.dp)) {
            Column(Modifier.fillMaxSize()) {
                Row(
                    Modifier
                        .background(SuperiorBackgroundColor)
                        .weight(0.4f)
                ) {
                    ImageCharacter(
                        characterDetailVO.image,
                        Modifier
                            .padding(8.dp)
                            .fillMaxHeight()
                            .fillMaxWidth(0.4f)
                    )
                    Column(Modifier.fillMaxWidth()) {
                        NameCharacter(
                            nameCharacter = characterDetailVO.name,
                            isDetail = true,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 16.dp)
                        )
                        TextBreedAndGenreCharacter(
                            genre = characterDetailVO.race,
                            breed = characterDetailVO.gender,
                            isDetail = true
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        TextOtherData("Base KI", characterDetailVO.ki, isDetail = true)
                        TextOtherData("Total KI", characterDetailVO.maxKi, isDetail = true)
                        TextOtherData("Afiliación", characterDetailVO.affiliation, isDetail = true)
                    }
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(InferiorBackgroundColor)
                        .weight(0.6f)
                        .verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false)
                ) {
                    TitleDescription(
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp)
                    )
                    SubTitleDescription(description = characterDetailVO.description)
                    if (characterDetailVO.transformations.isNotEmpty()) {
                        TitleTransformation(
                            Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(8.dp)
                        )
                        LazyRow {
                            items(characterDetailVO.transformations, key = { it.id }) {
                                ItemTransformation(it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemTransformation(item: Transformation) {
    Card(Modifier.padding(12.dp)) {
        Column(
            Modifier
                .size(200.dp)
                .background(SuperiorBackgroundColor)
                .padding(4.dp)
        ) {
            ImageCharacter(
                urlImage = item.image,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(120.dp)
            )
            NameCharacter(
                nameCharacter = item.name,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                isDetail = true
            )
            KiTransformationCharacter(
                item.ki,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun TitleTransformation(modifier: Modifier = Modifier) {
    Text(
        text = "Transformaciones",
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.White,
        modifier = modifier
    )
}

@Composable
fun SubTitleDescription(description: String) {
    Text(
        text = description,
        color = Color.White,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun TitleDescription(modifier: Modifier = Modifier) {
    Text(
        text = "Descripción",
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.White,
        modifier = modifier
    )
}