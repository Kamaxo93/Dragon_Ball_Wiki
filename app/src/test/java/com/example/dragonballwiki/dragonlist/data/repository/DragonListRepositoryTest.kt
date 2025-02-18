package com.example.dragonballwiki.dragonlist.data.repository

import com.example.dragonballwiki.dragonlist.data.local.datasource.DragonListLocalDataSource
import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DragonListRepositoryTest {

    private lateinit var dragonListRepository: DragonListRepository

    @RelaxedMockK
    private lateinit var dragonListRemoteDataSource: DragonListRemoteDataSource

    @RelaxedMockK
    private lateinit var dragonListLocalDataSource: DragonListLocalDataSource

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        dragonListRepository = DragonListRepositoryImpl(remoteDataSource = dragonListRemoteDataSource, localDataSource = dragonListLocalDataSource)
    }

    @Test
    fun `when the api doesnt return anything then get values from database`(): Unit = runBlocking {
        //Given
        coEvery { dragonListRemoteDataSource.getCharacterList() } returns listOf()

    }

}