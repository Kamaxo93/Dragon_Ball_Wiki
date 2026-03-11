package com.example.dragonballwiki.core.navigation

import com.example.dragonballwiki.core.Constant.CHARACTER_ID

sealed class Routes(val route: String) {
    data object DragonList : Routes(Route.DragonList.route)

    data object CharactersDetail : Routes(Route.CharactersDetail.route) {
        fun createRoute(id: String) =
            "$route/$id"
    }
}

enum class Route(val route: String) {
    DragonList("Dragon_List"),
    CharactersDetail("Character_Detail")
}