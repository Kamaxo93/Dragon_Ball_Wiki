package com.example.dragonballwiki.core.navigation

sealed class Routes(val route: String) {
    data object DragonList: Routes("Dragon_List")

    data object CharactersDetail: Routes("Character_Detail/{id}")
}