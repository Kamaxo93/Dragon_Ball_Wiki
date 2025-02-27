package com.example.dragonballwiki.dragonlist.data.repository

import android.content.SharedPreferences
import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.data.remote.service.DragonListService
import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.justRun
import io.mockk.mockk
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
    fun `when the api returns success and returns a list with dragon ball list`() = runTest {
        //Given
        every { dragonListLocalDataSource.getCharactersList() } returns flowOf(emptyList())
        coEvery { dragonListRemoteDataSource.getCharacterList() } returns listOf(
            CharacterBO(
                affiliation = "imperdiet",
                description = "enim",
                gender = "purus",
                id = 5864,
                image = "referrentur",
                ki = "honestatis",
                maxKi = "wisi",
                name = "Rodrigo Odom",
                race = "molestiae"

            )
        )
        every { sharedPreferences.getLong(any(), any()) } returns 0
        justRun {
            sharedPreferences.edit().putLong(any(), any()).apply()
        }
        coJustRun {
            dragonListLocalDataSource.addCharacters(any())
        }

        //When
        dragonListRepository.getCharacterList()

        //Then
        coVerify(exactly = 1) {
            dragonListRemoteDataSource.getCharacterList()
        }
    }

}