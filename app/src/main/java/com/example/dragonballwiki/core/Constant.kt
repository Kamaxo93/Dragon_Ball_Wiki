package com.example.dragonballwiki.core

object Constant {
    const val CHARACTER_ID: String = "CHARACTER_ID"
    const val BASE_URL: String = "https://dragonball-api.com/api/"
    const val NAME_DATA_BASE: String = "DragonBallDataBase"
    
    /**
     * Default limit for character list API requests.
     * The DragonBall API supports pagination, this is the page size.
     */
    const val DEFAULT_CHARACTER_LIMIT: Int = 100
}
