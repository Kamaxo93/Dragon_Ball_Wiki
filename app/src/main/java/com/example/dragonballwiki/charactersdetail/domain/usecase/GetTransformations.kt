package com.example.dragonballwiki.charactersdetail.domain.usecase

import com.example.dragonballwiki.charactersdetail.data.remote.mapper.toVO
import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.charactersdetail.ui.model.Transformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetTransformations {
    suspend operator fun invoke(id: Int): Flow<List<Transformation>>
}

class GetTransformationsImpl(private val repository: CharacterDetailRepository) : GetTransformations {

    override suspend fun invoke(id: Int): Flow<List<Transformation>> {
        return repository.getTransformation(id).map { it.toVO() }
    }

}