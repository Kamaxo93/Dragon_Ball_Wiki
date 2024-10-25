package com.example.dragonballwiki.dragonlist.data.repository

import com.example.dragonballwiki.core.AsyncResult
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import com.example.dragonballwiki.dragonlist.ui.model.CharactersVO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DragonListRepositoryImplTest {

    private lateinit var dragonListRepository: DragonListRepository

    @MockK
    private lateinit var dragonListRemoteDataSource: DragonListRemoteDataSource

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        dragonListRepository = DragonListRepositoryImpl(dragonListRemoteDataSource)
    }

    @Test
    fun `when getCharacterListRemote is called, then emit characterList response from remote data source`() = runTest {

        val expectedResponse = CharactersVO(listOf())

        coEvery {
            dragonListRemoteDataSource.getCharacterList()
        } returns expectedResponse

        val result = dragonListRepository.getCharacterListRemote().last()
        val response = (result as? AsyncResult.Success)?.data

        // Then
        assert(result is AsyncResult.Success)
        assertEquals(expectedResponse, response)

    }

    @Test
    fun `when getCharacterListRemote is called and remote data source throws exception, then emit an error`() = runTest {
        // Given
        coEvery { dragonListRemoteDataSource.getCharacterList() } throws Exception()

        // When
        val result = dragonListRepository.getCharacterListRemote().last()

        // Then
        assert(result is AsyncResult.Error)
    }

}