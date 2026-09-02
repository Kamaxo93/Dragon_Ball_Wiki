package com.example.dragonballwiki.charactersdetail.data.remote.datasource

import com.example.dragonballwiki.charactersdetail.data.remote.mapper.toVO
import com.example.dragonballwiki.charactersdetail.data.remote.model.CharacterDetailResponse
import com.example.dragonballwiki.charactersdetail.data.remote.service.CharacterDetailService
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO

interface CharacterDetailRemoteDataSource {
    suspend fun getCharacterDetail(id: String): CharacterDetailVO
    
    /**
     * Obtiene el CharacterDetailResponse sin convertir a VO
     * Útil para el repositorio que necesita guardar en Room
     */
    suspend fun getCharacterDetailResponse(id: String): CharacterDetailResponse
}

class CharacterDetailRemoteDataSourceImpl(
    private val service: CharacterDetailService
) :
    CharacterDetailRemoteDataSource {
    override suspend fun getCharacterDetail(id: String): CharacterDetailVO {
        return service.getCharacterDetail(id).toVO()
    }
    
    override suspend fun getCharacterDetailResponse(id: String): CharacterDetailResponse {
        return service.getCharacterDetail(id)
    }
}