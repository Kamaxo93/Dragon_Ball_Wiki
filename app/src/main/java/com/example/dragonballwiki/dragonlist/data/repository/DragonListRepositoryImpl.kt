package com.example.dragonballwiki.dragonlist.data.repository

import android.util.Log
import com.example.dragonballwiki.core.RepositoryErrorManager
import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO
import com.example.dragonballwiki.dragonlist.domain.model.toBO
import com.example.dragonballwiki.dragonlist.domain.model.toEntity
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DragonListRepositoryImpl(
    private val remoteDataSource: DragonListRemoteDataSource,
    private val localDataSource: DragonListLocalDataSource
) :
    DragonListRepository {
    
    companion object {
        private const val TAG = "DragonList-Repository"
    }

    override suspend fun getCharacterList(): Flow<List<CharacterBO>> {
        Log.d(TAG, "📚 getCharacterList() - Reading from local DB (Room)")
        return localDataSource.getCharactersList()
            .map {
                Log.d(TAG, "📦 Room emitted ${it.size} entities on ${Thread.currentThread().name} - Converting to BO")
                it.toBO()
            }
            .flowOn(Dispatchers.IO)
            // flowOn() changes dispatcher UPSTREAM (Room query + map transformation)
            // Collector (ViewModel) receives emissions on its own context (Main)
    }

    override suspend fun addCharactersLocalDataBase(): Flow<AsyncResult<Unit>> {
        Log.d(TAG, "💾 addCharactersLocalDataBase() - Starting remote fetch")
        return flow {
            getCharacterListRemote().collect {
                Log.d(TAG, "🌐 Remote fetch result on ${Thread.currentThread().name}: ${it::class.simpleName}")
                when (it) {
                    is AsyncResult.Success -> {
                        Log.i(TAG, "✅ Remote fetch successful - ${it.data.size} characters received")
                        Log.d(TAG, "💿 Saving ${it.data.size} characters to Room DB")
                        localDataSource.addCharacters(it.data.toEntity())
                        Log.i(TAG, "✅ Characters saved to Room DB successfully")
                        emit(AsyncResult.Success(Unit))
                    }

                    is AsyncResult.Error -> {
                        Log.e(TAG, "❌ Remote fetch error: ${it.error}")
                        emit(AsyncResult.Error(it.error))
                    }

                    is AsyncResult.Loading -> {
                        Log.d(TAG, "⏳ Remote fetch in progress...")
                        emit(AsyncResult.Loading())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
        // flowOn() ensures HTTP call and DB write execute on IO thread
    }

    private suspend fun getCharacterListRemote(): Flow<AsyncResult<List<CharacterBO>>> {
        Log.d(TAG, "🚀 getCharacterListRemote() - Calling remoteDataSource with ErrorManager wrap")
        return RepositoryErrorManager.wrap { remoteDataSource.getCharacterList() }
    }
}
