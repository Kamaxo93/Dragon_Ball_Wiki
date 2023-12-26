package com.example.dragonballwiki.dragonlist.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dragonballwiki.R
import com.example.dragonballwiki.ui.theme.ProgressIndicatorLogin

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DragonListScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LoginBall(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun DragonBallList(modifier: Modifier) {
}

@Composable
fun LoginBall(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier.size(150.dp), color = ProgressIndicatorLogin)
    Image(painter = painterResource(id = R.drawable.dragon_four_start), contentDescription = "", modifier.size(280.dp))
}
