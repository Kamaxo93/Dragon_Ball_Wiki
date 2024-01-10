package com.example.dragonballwiki.charactersdetail.data.datasource

import com.example.dragonballwiki.charactersdetail.data.remote.mapper.toVO
import com.example.dragonballwiki.charactersdetail.data.remote.service.CharacterDetailService
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO

interface CharacterDetailDataSource {
    suspend fun getCharacterDetail(id: String): CharacterDetailVO
}

class CharacterDetailDataSourceImpl(
    private val service: CharacterDetailService
) :
    CharacterDetailDataSource {
    override suspend fun getCharacterDetail(id: String): CharacterDetailVO {
        return service.getCharacterDetail(id).toVO()
    }
}