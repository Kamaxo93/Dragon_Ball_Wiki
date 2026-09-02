package com.example.dragonballwiki.charactersdetail.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailEntity
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailWithTransformations
import com.example.dragonballwiki.charactersdetail.data.local.model.TransformationEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operaciones de CharacterDetail con caché inteligente
 */
@Dao
interface CharacterDetailDao {
    
    /**
     * Obtiene el detalle completo de un personaje con transformaciones
     */
    @Transaction
    @Query("SELECT * FROM character_detail WHERE id = :characterId")
    fun getCharacterDetailWithTransformations(characterId: Int): Flow<CharacterDetailWithTransformations?>
    
    /**
     * Obtiene solo la entidad del detalle (sin transformaciones)
     * Útil para verificar timestamp
     */
    @Query("SELECT * FROM character_detail WHERE id = :characterId")
    suspend fun getCharacterDetail(characterId: Int): CharacterDetailEntity?
    
    /**
     * Inserta o actualiza el detalle del personaje
     * REPLACE strategy actualiza si ya existe
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterDetail(detail: CharacterDetailEntity)
    
    /**
     * Inserta transformaciones (borra las previas si existen por CASCADE)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransformations(transformations: List<TransformationEntity>)
    
    /**
     * Borra las transformaciones de un personaje
     * Necesario para actualizar correctamente
     */
    @Query("DELETE FROM transformation WHERE characterId = :characterId")
    suspend fun deleteTransformationsByCharacterId(characterId: Int)
    
    /**
     * Operación transaccional para guardar detalle + transformaciones
     */
    @Transaction
    suspend fun saveCharacterDetailComplete(
        detail: CharacterDetailEntity,
        transformations: List<TransformationEntity>
    ) {
        insertCharacterDetail(detail)
        deleteTransformationsByCharacterId(detail.id)
        if (transformations.isNotEmpty()) {
            insertTransformations(transformations)
        }
    }
    
    /**
     * Verifica si un detalle está cacheado y no ha expirado
     * @param characterId ID del personaje
     * @param expirationTime Timestamp límite (System.currentTimeMillis() - 10 días)
     * @return true si existe y no ha expirado
     */
    @Query("SELECT EXISTS(SELECT 1 FROM character_detail WHERE id = :characterId AND lastUpdated > :expirationTime)")
    suspend fun isCacheValid(characterId: Int, expirationTime: Long): Boolean
}
