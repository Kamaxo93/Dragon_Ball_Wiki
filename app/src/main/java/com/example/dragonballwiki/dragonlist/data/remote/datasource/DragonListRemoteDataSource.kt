package com.example.dragonballwiki.dragonlist.data.remote.datasource

import android.content.Context
import com.example.dragonballwiki.core.ErrorResponse
import com.example.dragonballwiki.core.NetWorkError
import com.example.dragonballwiki.core.Resource
import com.example.dragonballwiki.core.isOnline
import com.example.dragonballwiki.dragonlist.data.remote.mapper.toVo
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import com.google.gson.Gson
import java.lang.Exception

interface DragonListRemoteDataSource {
    suspend fun getCharacterList(): Resource<CharactersVO>
}

class DragonListRemoteDataSourceImpl(
    private val service: DragonListService,
    private val context: Context
) : DragonListRemoteDataSource {
    override suspend fun getCharacterList(): Resource<CharactersVO> {
        return if (isOnline(context)) {
            val response = service.getCharacterList(100)
            try {
                if (response.isSuccessful &&
                    response.body() != null
                ) {
                    Resource.Success(response.body()!!.toVo())
                } else {
                    Resource.ErrorsResponse(
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    )
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "en el servicio")
            }

        } else {
            NetWorkError("error de conexión")
        }
    }
}