package com.example.dragonballwiki.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.dragonballwiki.charactersdetail.ui.screen.CharacterDetailScreen
import com.example.dragonballwiki.dragonlist.ui.screen.DragonListScreen
import com.example.dragonballwiki.dragonlist.ui.viewmodel.DragonListViewModel

@Composable
fun NavigationHost() {
    var searchCharacter by remember { mutableStateOf("") }
    val navigationController = rememberNavController()
    val viewModel: DragonListViewModel = hiltViewModel()
    NavHost(
        navController = navigationController,
        startDestination = DragonBallList
    ) {
        viewModel.initializeDataState()
        composable<DragonBallList> {
            DragonListScreen(
                state = viewModel.state,
                searchCharacter = searchCharacter,
                onChangeSearchCharacter = {
                    searchCharacter = it
                },
                onClickElement = {
                    navigationController.navigate(CharacterDetail(it)) {
                        launchSingleTop = true
                    }
                },
                onReloadClicked = {
                    viewModel.reloadCharacterData()
                },
                onSearchCharacter = {
                    viewModel.searchCharacter(it)
                }
            )
        }

        composable<CharacterDetail> { backStackEntry ->
            val characterDetail: CharacterDetail = backStackEntry.toRoute()
            CharacterDetailScreen(id = characterDetail.id)
        }
    }
}