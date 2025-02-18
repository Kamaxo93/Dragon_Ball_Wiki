package com.example.dragonballwiki.charactersdetail.data.repository

import com.example.dragonballwiki.charactersdetail.data.local.datasource.CharacterDetailLocalDataSource
import com.example.dragonballwiki.charactersdetail.data.local.model.CharacterDetailEntity
import com.example.dragonballwiki.charactersdetail.data.local.model.TransformationEntity
import com.example.dragonballwiki.charactersdetail.data.remote.datasource.CharacterDetailRemoteDataSource
import com.example.dragonballwiki.charactersdetail.data.remote.mapper.toEntity
import com.example.dragonballwiki.charactersdetail.data.remote.mapper.toVO
import com.example.dragonballwiki.charactersdetail.data.remote.mapper.toVo
import com.example.dragonballwiki.charactersdetail.domain.repository.CharacterDetailRepository
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.core.RepositoryErrorManager
import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last

class CharacterDetailRepositoryImpl(
    private val remoteDataSource: CharacterDetailRemoteDataSource,
    private val dragonListLocalDataSource: DragonListLocalDataSource,
    private val detailLocalDataSource: CharacterDetailLocalDataSource,
) :
    CharacterDetailRepository {

    override suspend fun getCharacterDetailServer(id: String) =
        RepositoryErrorManager.wrap {
            remoteDataSource.getCharacterDetail(id)
        }

    override suspend fun getCharacterDetailLocal(id: Int): Flow<CharacterDetailVO> {
        return flow {
            try {
                val responseLocal = detailLocalDataSource.getCharacterWithTransformations(id)
                responseLocal.collect {
                    if (it != null) {
                        emit(it.toVo())

                    } else {
                        getCharacterDetailServer(id.toString()).collect { responseService ->
                            when (responseService) {
                                is AsyncResult.Error -> {
                                    throw Exception()
                                }

                                is AsyncResult.Loading -> {
                                    //no-op
                                }

                                is AsyncResult.Success -> {
                                    insertCharacter(responseService.data.toEntity())
                                    insertTransformation(responseService.data.transformations.toEntity(id))
                                }
                            }
                        }
                    }
                }

            } catch (e: Exception) {
                dragonListLocalDataSource.getCharacter(id.toString()).collect {
                    emit(it.toVO())
                }
            }
        }
    }

    override suspend fun insertCharacter(characterEntity: CharacterDetailEntity) {
        detailLocalDataSource.insertCharacter(characterEntity)
    }

    override suspend fun insertTransformation(transformationEntity: List<TransformationEntity>) {
        detailLocalDataSource.insertTransformation(transformationEntity)
    }

    override suspend fun getTransformation(characterId: Int): Flow<List<TransformationEntity>> {
        return detailLocalDataSource.getTransformation(characterId)
    }


}
