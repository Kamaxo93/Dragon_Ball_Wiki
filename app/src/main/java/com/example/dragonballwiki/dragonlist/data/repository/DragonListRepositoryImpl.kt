package com.example.dragonballwiki.dragonlist.data.repository

import android.content.SharedPreferences
import com.example.dragonballwiki.core.Constant.DEFAULT_LONG
import com.example.dragonballwiki.core.Constant.THREE_DAY_MILLI_SECONDS
import com.example.dragonballwiki.core.Constant.TIME_RELOAD
import com.example.dragonballwiki.core.RepositoryErrorManager
import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.core.di.MySharedPrefs
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO
import com.example.dragonballwiki.dragonlist.domain.model.toBO
import com.example.dragonballwiki.dragonlist.domain.model.toEntity
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.ZoneOffset

class DragonListRepositoryImpl(
    private val remoteDataSource: DragonListRemoteDataSource,
    private val localDataSource: DragonListLocalDataSource,
    @MySharedPrefs private val sharedPreferences: SharedPreferences,
) : DragonListRepository {

    private val timeNow = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()

    override suspend fun getCharacterList(): Flow<List<CharacterBO>> {
        val list = localDataSource.getCharactersList().map {
            it.toBO()
        }

        return flow {
            list.collect {
                if (it.isEmpty() ||
                    timeNow >= getTimeReload()) {
                    try {
                        addCharactersLocalDataBase()
                    } catch (e: Exception) {
                        emit(listOf())
                    }

                } else {
                    emit(it)
                }
            }
        }
    }

    private suspend fun addCharactersLocalDataBase() {
        sharedPreferences.edit().putLong(
            TIME_RELOAD, LocalDateTime.now().toInstant(
                ZoneOffset.UTC
            ).toEpochMilli()
        ).apply()

        getCharacterListRemote().collect {
                when (it) {
                    is AsyncResult.Success -> {
                        localDataSource.addCharacters(it.data.toEntity())
                    }

                    is AsyncResult.Error -> {
                        throw Exception()
                    }
                    is AsyncResult.Loading -> {
                        //no-op
                    }
                }
            }
        }

    private suspend fun getCharacterListRemote(): Flow<AsyncResult<List<CharacterBO>>> =
        RepositoryErrorManager.wrap { remoteDataSource.getCharacterList() }

    private fun getTimeReload(): Long = sharedPreferences.getLong(TIME_RELOAD, DEFAULT_LONG) + THREE_DAY_MILLI_SECONDS
}
