package com.example.dragonballwiki.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.dragonballwiki.charactersdetail.ui.screen.CharacterDetailScreen
import com.example.dragonballwiki.dragonlist.ui.screen.DragonListScreen

@Composable
fun NavigationHost() {
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = DragonBallList
    ) {

        composable<DragonBallList> { backStackEntry ->
            DragonListScreen { id ->
                navigationController.navigate(CharacterDetail(id))
            }
        }

        composable<CharacterDetail> { backStackEntry ->
            val characterDetail: CharacterDetail = backStackEntry.toRoute()
            CharacterDetailScreen(id = characterDetail.id)
        }
    }
}