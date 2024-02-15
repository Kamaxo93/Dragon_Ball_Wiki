package com.example.dragonballwiki.charactersdetail.data.remote.datasource

import com.example.dragonballwiki.charactersdetail.data.remote.mapper.toVO
import com.example.dragonballwiki.charactersdetail.data.remote.service.CharacterDetailService
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO

interface CharacterDetailRemoteDataSource {
    suspend fun getCharacterDetail(id: String): CharacterDetailVO
}

class CharacterDetailRemoteDataSourceImpl(
    private val service: CharacterDetailService
) :
    CharacterDetailRemoteDataSource {
    override suspend fun getCharacterDetail(id: String): CharacterDetailVO {
        return service.getCharacterDetail(id).toVO()
    }
}