package com.example.dragonballwiki.charactersdetail.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.charactersdetail.ui.viewmodel.CharacterDetailViewModel
import com.example.dragonballwiki.dragonlist.ui.compose.ImageCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.NameCharacter
import com.example.dragonballwiki.dragonlist.ui.uistate.CharacterUiState


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
            .background(Color.Red)) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
                .weight(0.7f)) {
            ImageCharacter("https://res.cloudinary.com/dgtgbyo76/image/upload/v1699044374/hlpy6q013uw3itl5jzic.webp", Modifier.padding(8.dp))
            Column(Modifier.weight(1f)) {
                NameCharacter(nameCharacter = "Goku")

            }


        }
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.Magenta)
                .weight(1f)) {

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreenPreview() {
    CharacterDetailScreen(characterDetailViewModel = null, id = null)
}