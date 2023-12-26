package com.example.dragonballwiki.dragonlist.data.remote.datasource

import android.accounts.NetworkErrorException
import android.content.Context
import com.example.dragonballwiki.core.isOnline
import com.example.dragonballwiki.dragonlist.data.remote.mapper.toVo
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO

interface DragonListRemoteDataSource {
    suspend fun getCharacterList(): CharactersVO
}

class DragonListRemoteDataSourceImpl(
    private val service: DragonListService,
    private val context: Context
) : DragonListRemoteDataSource {
    override suspend fun getCharacterList(): CharactersVO {
        return if (isOnline(context)) {
            service.getCharacterList(100).body()?.toVo() ?: CharactersVO(listOf())

        } else {
            throw NetworkErrorException()
        }
    }
}