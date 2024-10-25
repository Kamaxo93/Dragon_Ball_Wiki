package com.example.dragonballwiki

import com.example.dragonballwiki.dragonlist.data.remote.datasource.DragonListRemoteDataSource
import com.example.dragonballwiki.dragonlist.data.repository.DragonListRepositoryImpl
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class DragonListRepositoryTest {

    private lateinit var dragonListRepository: DragonListRepository

    @MockK
    private lateinit var dragonListRemoteDataSource: DragonListRemoteDataSource

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        dragonListRepository = DragonListRepositoryImpl(dragonListRemoteDataSource)
    }


}