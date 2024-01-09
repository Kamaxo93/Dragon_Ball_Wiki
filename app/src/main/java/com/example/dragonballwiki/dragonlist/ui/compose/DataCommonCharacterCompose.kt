package com.example.dragonballwiki.dragonlist.ui.compose

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