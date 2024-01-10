package com.example.dragonballwiki.dragonlist.data.remote.service

import com.example.dragonballwiki.dragonlist.data.remote.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DragonListService {

    @GET("characters/")
    suspend fun getCharacterList(@Query("limit") limit: Int): CharactersResponse

}