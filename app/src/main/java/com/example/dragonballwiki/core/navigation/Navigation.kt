package com.example.dragonballwiki.core.navigation

import android.app.Activity
import android.util.Log
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    NavHost(
        navController = navigationController,
        startDestination = Routes.DragonList.route,

    ) {

        composable(route = Routes.DragonList.route) {
            DragonListScreen(
                onClickElement = {
                    navigationController.navigate(Routes.CharactersDetail.createRoute(it)) {
                        launchSingleTop = true
                    }
                })
            BackHandler(enabled = true)  {
                if (!navigationController.popBackStack()) {
                    (context as? Activity)?.finish()
                }
            }
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