package com.example.dragonballwiki.dragonlist.data.remote.datasource

import com.example.dragonballwiki.dragonlist.data.remote.model.CharactersResponse
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO
import com.example.dragonballwiki.mockdata.fillMockDataDragonList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DragonListRemoteDataSourceTest {

    private lateinit var remoteDataSource: DragonListRemoteDataSource

    private val server: DragonListService = mockk()

    @Before
    fun onBefore() {
        remoteDataSource = DragonListRemoteDataSourceImpl(server)
    }


    @Test
    fun `when the api response success should return a list of dragons`() = runTest {
        //Given
        coEvery { server.getCharacterList(100) } returns fillMockDataDragonList()

        //When
        val response = remoteDataSource.getCharacterList()

        //Then
        assertTrue(response.isNotEmpty())
    }

    @Test
    fun `when the api response success should returns list empty`() = runTest {
        //Given
        coEvery { server.getCharacterList(100) } returns CharactersResponse(emptyList())

        //When
        val response = remoteDataSource.getCharacterList()

        //Then
        coVerify(exactly = 1) { server.getCharacterList(100) }
        assertTrue(response.isEmpty())
    }

    @Test
    fun `when the api response success should returns mapper list`() = runTest {
        //Given
        coEvery { server.getCharacterList(100) } returns CharactersResponse(emptyList())

        //When
        val response = remoteDataSource.getCharacterList()

        //Then
        assertTrue(response is List<CharacterBO>)
    }

}