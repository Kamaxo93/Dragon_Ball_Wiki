package com.example.dragonballwiki.dragonlist.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.dragonballwiki.R

@Composable
fun NameCharacter(nameCharacter: String, modifier: Modifier = Modifier, isDetail: Boolean = false) {
    Text(
        text = nameCharacter,
        fontWeight = FontWeight.ExtraBold,
        fontSize = if (isDetail.not()) 32.sp else 16.sp,
        modifier = modifier.padding(6.dp),
        maxLines = 1
    )
}

@Composable
fun KiTransformationCharacter(kiTransformationCharacter: String, modifier: Modifier = Modifier) {
    Text(
        text = kiTransformationCharacter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
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
fun TextOtherData(title: String, data: String, isDetail: Boolean = false) {
    Column {
        Text(
            text = title,
            fontSize = if (isDetail.not()) 20.sp else 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )
        Text(
            text = data,
            fontSize = if (isDetail.not()) 20.sp else 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, bottom = if (isDetail.not()) 10.dp else 6.dp)
        )
    }
}

@Composable
fun TextBreedAndGenreCharacter(breed: String, genre: String, isDetail: Boolean = false) {
    Text(
        text = stringResource(R.string.character_list_label_breed_genre, breed, genre),
        fontSize = if (isDetail.not()) 18.sp else 14.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 16.dp, top = if (isDetail.not()) 8.dp else 4.dp)
    )
}