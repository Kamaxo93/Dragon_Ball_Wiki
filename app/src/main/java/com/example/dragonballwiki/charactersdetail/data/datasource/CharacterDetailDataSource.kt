package com.example.dragonballwiki.charactersdetail.data.datasource

import android.accounts.NetworkErrorException
import android.content.Context
import com.example.dragonballwiki.charactersdetail.data.remote.mapper.toVO
import com.example.dragonballwiki.charactersdetail.data.remote.service.CharacterDetailService
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.core.isOnline

interface CharacterDetailDataSource {
    suspend fun getCharacterDetail(id: String): CharacterDetailVO?
}

class CharacterDetailDataSourceImpl(
    private val service: CharacterDetailService,
    private val context: Context
) :
    CharacterDetailDataSource {
    override suspend fun getCharacterDetail(id: String): CharacterDetailVO? {
        return if (isOnline(context = context)) {
            service.getCharacterDetail(id).body()?.toVO()

        } else {
            throw NetworkErrorException()
        }
    }
}