package com.example.dragonballwiki.charactersdetail.ui.screen

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dragonballwiki.R
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.charactersdetail.ui.model.Transformation
import com.example.dragonballwiki.charactersdetail.ui.viewmodel.CharacterDetailViewModel
import com.example.dragonballwiki.core.isTrue
import com.example.dragonballwiki.dragonlist.ui.compose.ImageCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.KiTransformationCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.NameCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.TextBreedAndGenreCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.TextOtherData
import com.example.dragonballwiki.dragonlist.ui.screen.LoginBall


@Composable
fun CharacterDetailScreen(
    characterDetailViewModel: CharacterDetailViewModel = hiltViewModel(),
    id: String,
) {

    characterDetailViewModel.initializeDataState(id)
    val state = characterDetailViewModel.state

    when {
        state.error?.isNotEmpty().isTrue() -> {
            // Se carga el personaje desde la base de datos
        }

        state.loading -> {
            LoginBall(stringResource(R.string.character_detail_label_loading))
        }

        state.characterDetail != null -> {
            CharacterDetail(characterDetailVO = state.characterDetail)
        }
    }
}

@Composable
fun CharacterDetail(characterDetailVO: CharacterDetailVO) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var isExpanded by remember { mutableStateOf(true) }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        androidx.compose.animation.AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(animationSpec = tween(durationMillis = 500)) + fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = shrinkVertically(animationSpec = tween(durationMillis = 500)) + fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            Row(
                Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .fillMaxWidth()
                    .animateContentSize(animationSpec = tween(durationMillis = 500))
                    .clickable { isExpanded = !isExpanded }
            ) {
                ImageCharacter(
                    characterDetailVO.image,
                    Modifier
                        .padding(8.dp)
                        .fillMaxHeight()
                        .fillMaxWidth(0.4f)
                )
                Column(
                    Modifier
                        .fillMaxWidth()
                ) {
                    NameCharacter(
                        nameCharacter = characterDetailVO.name,
                        isDetail = true,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                    TextBreedAndGenreCharacter(
                        genre = characterDetailVO.gender,
                        breed = characterDetailVO.race,
                        isDetail = true
                    )
                    if (isLandscape) {
                        Row {
                            OtherDataCharacter(
                                characterDetailVO.ki,
                                characterDetailVO.maxKi,
                                characterDetailVO.affiliation
                            )
                        }
                    } else {
                        OtherDataCharacter(
                            characterDetailVO.ki,
                            characterDetailVO.maxKi,
                            characterDetailVO.affiliation
                        )
                    }
                }
            }
        }
        androidx.compose.animation.AnimatedVisibility(
            visible = !isExpanded,
            enter = expandVertically(animationSpec = tween(durationMillis = 500)) + fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = shrinkVertically(animationSpec = tween(durationMillis = 500)) + fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            Row(
                Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    )
                    .clickable { isExpanded = !isExpanded }
            ) {
                NameCharacter(
                    nameCharacter = characterDetailVO.name,
                    isDetail = true,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            TitleDescription(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
            SubTitleDescription(characterDetailVO.description)
            if (characterDetailVO.name.isNotEmpty()) {
                PlanetContent(characterDetailVO)
            }
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

@Composable
fun PlanetContent(characterDetailVO: CharacterDetailVO) {
    Column {
        TitlePlanet(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(8.dp))
        Column  {
            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                ImageCharacter(characterDetailVO.imagePlanet)
            }
            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                NameCharacter(characterDetailVO.namePlanet, modifier = Modifier.align(Alignment.CenterHorizontally))
                SubTitleDescription(characterDetailVO.descriptionPlanet)
            }
        }
    }
}

@Composable
fun ItemTransformation(item: Transformation) {
    Card(Modifier.padding(8.dp)) {
        Column(
            Modifier
                .size(230.dp)
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImageCharacter(
                urlImage = item.image,
                modifier = Modifier
                    .size(120.dp)
            )
            NameCharacter(
                nameCharacter = item.name,
                isDetail = true
            )
            KiTransformationCharacter(
                item.ki,
            )
        }
    }
}

@Composable
fun TitleTransformation(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.character_detail_label_transformations),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = modifier
    )
}

@Composable
fun TitlePlanet(modifier: Modifier = Modifier) {
    Text(
        text = "Planeta",
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = modifier
    )
}

@Composable
fun SubTitleDescription(description: String) {
    Text(
        text = description,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun OtherDataCharacter(ki: String, maxKi: String, affiliation: String, isDetail: Boolean = true) {

    TextOtherData(
        stringResource(R.string.base_ki),
        ki,
        isDetail = isDetail
    )

    TextOtherData(
        stringResource(R.string.maxi_ki),
        maxKi,
        isDetail = isDetail
    )

    TextOtherData(
        stringResource(R.string.affiliation),
        affiliation,
        isDetail = isDetail
    )
}

@Preview
@Composable
fun TitleDescription(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.title_description),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = modifier,
    )
}