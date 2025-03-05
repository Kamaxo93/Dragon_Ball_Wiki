package com.example.dragonballwiki.dragonlist.data.repository

import android.content.SharedPreferences
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.data.remote.mapper.toBo
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO
import com.example.dragonballwiki.dragonlist.domain.model.toEntity
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import com.example.dragonballwiki.mockdata.fillMockDataDragonList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DragonListRepositoryTest {

    private val dragonListRemoteDataSource: DragonListRemoteDataSource = mockk()

    private val dragonListLocalDataSource: DragonListLocalDataSource = mockk()

    private val sharedPreferences: SharedPreferences = mockk()

    private lateinit var dragonListRepository: DragonListRepositoryImpl

    @Before
    fun onBefore() {
        dragonListRepository = DragonListRepositoryImpl(remoteDataSource = dragonListRemoteDataSource, localDataSource = dragonListLocalDataSource, sharedPreferences = sharedPreferences)
    }

    @Test
    fun `when the database returns success and returns a list with dragon ball list`() = runTest {
        //Given
        coEvery { dragonListLocalDataSource.getCharactersList() } returns flowOf(fillMockDataDragonList().characters.map { it.toBo() }.toEntity())
        every { sharedPreferences.getLong(any(), any()) } returns 99999999999999

        //When
        val result = dragonListRepository.getCharacterList().first()

        //Then
        coVerify(exactly = 1) {
            dragonListLocalDataSource.getCharactersList()
        }

        assertTrue(fillMockDataDragonList().characters.map { it.toBo() } == result)

    }

    @Test
    fun `when the database returns empty list`() = runTest {
        //Given
        coEvery { dragonListLocalDataSource.getCharactersList() } returns flowOf(listOf())

        //When
        val result = dragonListRepository.getCharacterList().first()

        //Then
        coVerify(exactly = 1) {
            dragonListLocalDataSource.getCharactersList()
        }

       assertTrue(result.isEmpty())
    }

    @Test
    fun `when the database returns empty and call to remote data source and returns a list with dragon ball list`() = runTest {
        //Given
        coEvery { dragonListLocalDataSource.getCharactersList() } returns flowOf(listOf())
        coEvery { dragonListRemoteDataSource.getCharacterList() } returns fillMockDataDragonList().characters.map { it.toBo() }
        every { sharedPreferences.getLong(any(), any()) } returns 0
//        //Mockean las funciones que retornan Unit
        justRun {
            sharedPreferences.edit().putLong(any(), any()).apply()
        }

        //When
       dragonListRepository.getCharacterList().first()

        //Then
        coVerify(exactly = 1) {
            dragonListLocalDataSource.getCharactersList()
            dragonListLocalDataSource.addCharacters(fillMockDataDragonList().characters.map { it.toBo() }.toEntity())
            dragonListRemoteDataSource.getCharacterList()
        }
    }

//    TODO: Revisar mas tarde
//    @Test
//    fun `when the database returns empty and call to remote data source and returns a error`() = runTest {
//        //Given
//        val exception = Exception()
//        coEvery { dragonListLocalDataSource.getCharactersList() } returns flowOf(listOf())
//        coEvery { dragonListRemoteDataSource.getCharacterList() } throws exception
//        every { sharedPreferences.getLong(any(), any()) } returns 0
////        //Mockean las funciones que retornan Unit
//        justRun {
//            sharedPreferences.edit().putLong(any(), any()).apply()
//        }
//
//        //When
//       dragonListRepository.getCharacterList().first()
//
//        //Then
//        coVerify(exactly = 1) {
//            dragonListLocalDataSource.getCharactersList()
//            dragonListLocalDataSource.addCharacters(fillMockDataDragonList().characters.map { it.toBo() }.toEntity())
//            dragonListRemoteDataSource.getCharacterList()
//        }
//    }
}