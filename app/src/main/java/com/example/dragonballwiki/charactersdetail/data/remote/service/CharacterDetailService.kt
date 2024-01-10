package com.example.dragonballwiki.charactersdetail.data.remote.service

import com.example.dragonballwiki.charactersdetail.data.remote.model.CharacterDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterDetailService {
    @GET("/api/characters/{id}")
    suspend fun getCharacterDetail(@Path("id") id: String): CharacterDetailResponse
}