package com.example.dragonballwiki.charactersdetail.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.charactersdetail.ui.model.Transformation
import com.example.dragonballwiki.charactersdetail.ui.viewmodel.CharacterDetailViewModel
import com.example.dragonballwiki.dragonlist.ui.compose.ImageCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.KiTransformationCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.NameCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.TextBreedAndGenreCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.TextOtherData
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState
import com.example.dragonballwiki.ui.theme.ColumnBackgroundColor
import com.example.dragonballwiki.ui.theme.InferiorBackgroundColor
import com.example.dragonballwiki.ui.theme.SuperiorBackgroundColor


@Composable
fun CharacterDetailScreen(characterDetailViewModel: CharacterDetailViewModel?, id: String?) {

//    val lifecycle = LocalLifecycleOwner.current.lifecycle
//
//    val uiState by produceState<CharacterUiState>(
//        initialValue = CharacterUiState.Loading,
//        key1 = lifecycle,
//        key2 = characterDetailViewModel
//    ) {
//        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
//            characterDetailViewModel.uiState.collect { value = it }
//        }
//    }
//    characterDetailViewModel.getCharacterDetail(id)
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
                        "https://res.cloudinary.com/dgtgbyo76/image/upload/v1699044374/hlpy6q013uw3itl5jzic.webp",
                        Modifier.padding(8.dp)
                    )
                    Column(Modifier.fillMaxWidth()) {
                        NameCharacter(
                            nameCharacter = "Goku", isDetail = true, modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 16.dp)
                        )
                        TextBreedAndGenreCharacter(
                            genre = "Saiyan",
                            breed = "Male",
                            isDetail = true
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        TextOtherData("Base KI", "character.ki", isDetail = true)
                        TextOtherData("Total KI", "character.maxKi", isDetail = true)
                        TextOtherData("Afiliación", "character.affiliation", isDetail = true)
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
                    SubTitleDescription(description = "El protagonista de la serie, conocido por su gran poder y personalidad amigable. Originalmente enviado a la Tierra como un infante volador con la misión de conquistarla. Sin embargo, el caer por un barranco le proporcionó un brutal golpe que si bien casi lo mata, este alteró su memoria y anuló todos los instintos violentos de su especie, lo que lo hizo crecer con un corazón puro y bondadoso, pero conservando todos los poderes de su raza. No obstante, en la nueva continuidad de Dragon Ball se establece que él fue enviado por sus padres a la Tierra con el objetivo de sobrevivir a toda costa a la destrucción de su planeta por parte de Freeza. Más tarde, Kakarot, ahora conocido como Son Goku, se convertiría en el príncipe consorte del monte Fry-pan y líder de los Guerreros Z, así como el mayor defensor de la Tierra y del Universo 7, logrando mantenerlos a salvo de la destrucción en innumerables ocasiones, a pesar de no considerarse a sí mismo como un héroe o salvador.")
                    TitleTransformation(
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp)
                    )
                    LazyRow {
                        item {
                            ItemTransformation(Transformation(1, "https://res.cloudinary.com/dgtgbyo76/image/upload/v1699050838/x8vdjitiaygwbrbh3ps9.png", "3 Billon", "Goku SSJ"))
                        }
                        item {
                            ItemTransformation(Transformation(1, "https://res.cloudinary.com/dgtgbyo76/image/upload/v1699050838/x8vdjitiaygwbrbh3ps9.png", "3 Billon", "Goku SSJ"))
                        }
                        item {
                            ItemTransformation(Transformation(1, "https://res.cloudinary.com/dgtgbyo76/image/upload/v1699050838/x8vdjitiaygwbrbh3ps9.png", "3 Billon", "Goku SSJ"))
                        }
                        item {
                            ItemTransformation(Transformation(1, "https://res.cloudinary.com/dgtgbyo76/image/upload/v1699050838/x8vdjitiaygwbrbh3ps9.png", "3 Billon", "Goku SSJ"))
                        }
                        item {
                            ItemTransformation(Transformation(1, "https://res.cloudinary.com/dgtgbyo76/image/upload/v1699050838/x8vdjitiaygwbrbh3ps9.png", "3 Billon", "Goku SSJ"))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemTransformation(item: Transformation) {
    Card(Modifier.padding(16.dp)) {
        Column(Modifier.size(200.dp).background(SuperiorBackgroundColor).padding(4.dp)) {
            ImageCharacter(urlImage = item.image, modifier = Modifier.align(Alignment.CenterHorizontally).size(120.dp))
            NameCharacter(nameCharacter = item.name,  modifier = Modifier.align(Alignment.CenterHorizontally), isDetail = true)
            KiTransformationCharacter(item.ki,  modifier = Modifier.align(Alignment.CenterHorizontally))
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreenPreview() {
    CharacterDetailScreen(characterDetailViewModel = null, id = null)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemPreview() {
    ItemTransformation(Transformation(1, "https://res.cloudinary.com/dgtgbyo76/image/upload/v1699050838/x8vdjitiaygwbrbh3ps9.png", "3 Billon", "Goku SSJ"))
}