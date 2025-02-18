package com.example.dragonballwiki.charactersdetail.data.remote.datasource

import com.example.dragonballwiki.charactersdetail.data.remote.model.CharacterDetailResponse
import com.example.dragonballwiki.charactersdetail.data.remote.service.CharacterDetailService

interface CharacterDetailRemoteDataSource {
    suspend fun getCharacterDetail(id: String): CharacterDetailResponse
}

class CharacterDetailRemoteDataSourceImpl(
    private val service: CharacterDetailService
) :
    CharacterDetailRemoteDataSource {
    override suspend fun getCharacterDetail(id: String): CharacterDetailResponse {
        return service.getCharacterDetail(id)
    }
}