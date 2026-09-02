package com.example.dragonballwiki.charactersdetail.data.local.datasource

import com.example.dragonballwiki.charactersdetail.data.local.CharacterDetailDao
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailEntity
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailWithTransformations
import com.example.dragonballwiki.charactersdetail.data.local.model.TransformationEntity
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

/**
 * Constante para tiempo de expiración de caché: 10 días en milisegundos
 */
const val CACHE_EXPIRATION_DAYS = 10L
val CACHE_EXPIRATION_MILLIS = TimeUnit.DAYS.toMillis(CACHE_EXPIRATION_DAYS)

interface CharacterDetailLocalDataSource {
    
    /**
     * Obtiene el detalle completo con transformaciones desde local
     */
    fun getCharacterDetailWithTransformations(characterId: Int): Flow<CharacterDetailWithTransformations?>
    
    /**
     * Guarda el detalle completo con transformaciones
     */
    suspend fun saveCharacterDetail(detail: CharacterDetailEntity, transformations: List<TransformationEntity>)
    
    /**
     * Verifica si el caché es válido (existe y no ha expirado)
     * @return true si el caché existe y tiene menos de 10 días
     */
    suspend fun isCacheValid(characterId: Int): Boolean
}

class CharacterDetailLocalDataSourceImpl(
    private val dao: CharacterDetailDao
) : CharacterDetailLocalDataSource {
    
    override fun getCharacterDetailWithTransformations(characterId: Int): Flow<CharacterDetailWithTransformations?> {
        return dao.getCharacterDetailWithTransformations(characterId)
    }
    
    override suspend fun saveCharacterDetail(
        detail: CharacterDetailEntity,
        transformations: List<TransformationEntity>
    ) {
        dao.saveCharacterDetailComplete(detail, transformations)
    }
    
    override suspend fun isCacheValid(characterId: Int): Boolean {
        val expirationTime = System.currentTimeMillis() - CACHE_EXPIRATION_MILLIS
        return dao.isCacheValid(characterId, expirationTime)
    }
}
