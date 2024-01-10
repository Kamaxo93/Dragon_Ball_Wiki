package com.example.dragonballwiki.dragonlist.data.remote.datasource

import com.example.dragonballwiki.dragonlist.data.remote.mapper.toVo
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO

interface DragonListRemoteDataSource {
    suspend fun getCharacterList(): CharactersVO
}

class DragonListRemoteDataSourceImpl(
    private val service: DragonListService,
) : DragonListRemoteDataSource {
    override suspend fun getCharacterList(): CharactersVO {
        return service.getCharacterList(100).toVo()
    }
}