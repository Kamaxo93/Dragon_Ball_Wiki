package com.example.dragonballwiki.charactersdetail.data.repository

import com.example.dragonballwiki.charactersdetail.data.datasource.CharacterDetailDataSource
import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.charactersdetail.ui.model.OriginPlanet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterDetailRepositoryImpl(private val remoteDataSource: CharacterDetailDataSource) :
    CharacterDetailRepository {

    override fun getCharacterDetail(id: String): Flow<CharacterDetailVO> = flow {
        emit(remoteDataSource.getCharacterDetail(id) ?: CharacterDetailVO(
            affiliation = "",
            description = "",
            gender = "",
            id = 0,
            image = "",
            ki = "",
            maxKi = "",
            name = "",
            originPlanet = OriginPlanet(
                description = "",
                id = 0,
                image = "",
                isDestroyed = false,
                name = ""
            ),
            race = "",
            transformations = listOf()
        ))
    }
}