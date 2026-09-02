package com.example.dragonballwiki.charactersdetail.data.repository

import com.example.dragonballwiki.charactersdetail.data.local.datasource.CharacterDetailLocalDataSource
import com.example.dragonballwiki.charactersdetail.data.local.mapper.toEntity
import com.example.dragonballwiki.charactersdetail.data.local.mapper.toVO
import com.example.dragonballwiki.charactersdetail.data.remote.datasource.CharacterDetailRemoteDataSource
import com.example.dragonballwiki.charactersdetail.data.remote.mapper.toVO
import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.core.RepositoryErrorManager.toAsyncError
import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

/**
 * Implementación del repositorio de CharacterDetail con estrategia de caché inteligente
 * 
 * Estrategia Offline-First con actualización periódica:
 * 1. Verifica si existe caché válido (< 10 días)
 * 2. Si es válido: retorna datos locales
 * 3. Si no es válido o no existe: fetch remoto y guarda en caché
 * 4. Si fetch falla: intenta recuperar de caché expirado como fallback
 */
class CharacterDetailRepositoryImpl(
    private val remoteDataSource: CharacterDetailRemoteDataSource,
    private val localDataSource: DragonListLocalDataSource,
    private val detailLocalDataSource: CharacterDetailLocalDataSource
) : CharacterDetailRepository {

    override suspend fun getCharacterDetail(id: String): Flow<AsyncResult<CharacterDetailVO>> = flow {
        emit(AsyncResult.Loading())
        
        val characterId = id.toIntOrNull() ?: 0
        
        // PASO 1: Verificar si existe caché válido
        val isCacheValid = detailLocalDataSource.isCacheValid(characterId)
        
        if (isCacheValid) {
            // CACHÉ VÁLIDO: Retornar datos locales
            val cachedDetail = detailLocalDataSource
                .getCharacterDetailWithTransformations(characterId)
                .firstOrNull()
            
            if (cachedDetail != null) {
                emit(AsyncResult.Success(cachedDetail.toVO()))
                return@flow
            }
        }
        
        // PASO 2: Caché no válido o no existe - Fetch remoto
        try {
            val response = remoteDataSource.getCharacterDetailResponse(id)
            
            // Guardar en caché para futuras consultas
            detailLocalDataSource.saveCharacterDetail(
                detail = response.toEntity(),
                transformations = response.transformations.toEntity(characterId)
            )
            
            // Retornar datos convertidos a VO
            emit(AsyncResult.Success(response.toVO()))
            
        } catch (e: Exception) {
            // PASO 3: Error en fetch remoto - Intentar caché expirado como fallback
            val cachedDetail = detailLocalDataSource
                .getCharacterDetailWithTransformations(characterId)
                .firstOrNull()
            
            if (cachedDetail != null) {
                // Retornar caché expirado es mejor que nada
                emit(AsyncResult.Success(cachedDetail.toVO()))
            } else {
                // No hay caché: propagar el error convertido
                emit(AsyncResult.Error(e.toAsyncError()))
            }
        }
    }

    override suspend fun getCharacterDetailLocal(id: String): Flow<CharacterEntity> {
        return localDataSource.getCharacter(id)
    }
}