package com.example.dragonballwiki.dragonlist.data.remote.datasource

import com.example.dragonballwiki.core.Constant.DEFAULT_CHARACTER_LIMIT
import com.example.dragonballwiki.dragonlist.data.remote.mapper.toBo
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO

interface DragonListRemoteDataSource {
    suspend fun getCharacterList(): List<CharacterBO>
}

class DragonListRemoteDataSourceImpl(
    private val service: DragonListService,
) : DragonListRemoteDataSource {
    override suspend fun getCharacterList(): List<CharacterBO> {
        return service.getCharacterList(DEFAULT_CHARACTER_LIMIT).characters.map { it.toBo() }
    }
}