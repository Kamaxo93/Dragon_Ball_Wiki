package com.example.dragonballwiki.dragonlist.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun NameCharacter(nameCharacter: String, modifier: Modifier = Modifier, isDetail: Boolean = false) {
    Text(
        text = nameCharacter,
        fontWeight = FontWeight.ExtraBold,
        fontSize =  if (isDetail.not()) 32.sp else 16.sp,
        color = Color.White,
        modifier = modifier.padding(8.dp),
        maxLines = 1
    )
}

@Composable
fun KiTransformationCharacter(kiTransformationCharacter: String, modifier: Modifier = Modifier) {
    Text(
        text = kiTransformationCharacter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
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
fun TextOtherData(title: String, data: String, isDetail: Boolean = false) {
    Column {
        Text(
            text = title,
            fontSize = if (isDetail.not()) 20.sp else 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )
        Text(
            text = data,
            fontSize = if (isDetail.not()) 20.sp else 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFFBC02D),
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )
    }
}

@Composable
fun TextBreedAndGenreCharacter(breed: String, genre: String, isDetail: Boolean = false) {
    Text(
        text = "$breed - $genre",
        fontSize = if (isDetail.not()) 20.sp else 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFFFBC02D),
        modifier = Modifier.padding(start = 16.dp, top = 8.dp)
    )
}