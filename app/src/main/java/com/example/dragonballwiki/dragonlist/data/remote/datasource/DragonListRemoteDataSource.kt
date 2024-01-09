package com.example.dragonballwiki.dragonlist.data.remote.datasource

import android.content.Context
import com.example.dragonballwiki.core.ErrorResponse
import com.example.dragonballwiki.core.NetWorkError
import com.example.dragonballwiki.core.Resource
import com.example.dragonballwiki.core.isOnline
import com.example.dragonballwiki.core.responseService
import com.example.dragonballwiki.dragonlist.data.remote.mapper.toVo
import com.example.dragonballwiki.dragonlist.data.remote.model.CharactersResponse
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO

interface DragonListRemoteDataSource {
    suspend fun getCharacterList(): Resource<CharactersVO>
}

class DragonListRemoteDataSourceImpl(
    private val service: DragonListService,
    private val context: Context
) : DragonListRemoteDataSource {
    override suspend fun getCharacterList(): Resource<CharactersVO> {
        return if (isOnline(context)) {
            val response = responseService(service.getCharacterList(100))
            if (response is Resource.Success) {
                Resource.Success(response.data?.toVo() ?: CharactersVO(listOf()))

            } else {
                Resource.Error(response.message ?: response.errorResponse?.message ?: "error servicio")
            }

        } else {
            NetWorkError("error de conexión")
        }
    }
}