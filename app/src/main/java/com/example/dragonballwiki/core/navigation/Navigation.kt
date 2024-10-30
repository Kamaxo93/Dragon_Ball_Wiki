package com.example.dragonballwiki.core.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dragonballwiki.charactersdetail.ui.screen.CharacterDetailScreen
import com.example.dragonballwiki.core.Constant.CHARACTER_ID
import com.example.dragonballwiki.dragonlist.ui.screen.DragonListScreen

@Composable
fun NavigationHost() {

    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = Routes.DragonList.route
    ) {
        composable(route = Routes.DragonList.route) {
            DragonListScreen(
                onClickElement = {
                    navigationController.navigate(Routes.CharactersDetail.createRoute(it)) {
                        launchSingleTop = true
                    }
                })
        }

        composable(
            route = "${Routes.CharactersDetail.route}/?id={${NavArgs.CharacterId.key}}",
            arguments = listOf(
                navArgument(NavArgs.CharacterId.key) {
                    type = NavType.StringType
                })
        ) {
            CharacterDetailScreen()
        }
    }

}

enum class NavArgs(val key: String) {
    CharacterId(CHARACTER_ID)
}