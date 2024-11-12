package com.example.dragonballwiki.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.dragonballwiki.charactersdetail.ui.screen.CharacterDetailScreen
import com.example.dragonballwiki.dragonlist.ui.screen.DragonListScreen

@Composable
fun NavigationHost() {
    var searchCharacter by remember { mutableStateOf("") }
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = DragonBallList
    ) {

        composable<DragonBallList> { backStackEntry ->
            DragonListScreen(
                searchCharacter = searchCharacter,
                onChangeSearchCharacter = {
                    searchCharacter = it
                },
                onClickElement = {
                    navigationController.navigate(CharacterDetail(it))
                }
            )
        }

        composable<CharacterDetail> { backStackEntry ->
            val characterDetail: CharacterDetail = backStackEntry.toRoute()
            CharacterDetailScreen(id = characterDetail.id)
        }
    }
}