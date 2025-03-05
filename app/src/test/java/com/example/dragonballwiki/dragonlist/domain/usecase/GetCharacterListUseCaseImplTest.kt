package com.example.dragonballwiki.dragonlist.domain.usecase

import com.example.dragonballwiki.dragonlist.data.remote.mapper.toBo
import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import com.example.dragonballwiki.mockdata.fillMockDataDragonList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetCharacterDetailEntityListUseCaseTest {

    private val repository: DragonListRepository = mockk()

    private lateinit var useCase: GetCharacterListUseCase

    @Before
    fun onBefore() {
        useCase = GetCharacterListUseCaseImpl(repository)
    }

    @Test
    fun `when the repository return anything then get values from database is empty`() = runBlocking {
        //Given

        coEvery { repository.getCharacterList() } returns flow { emit(listOf()) }

        //When
         val response = useCase()

        //Then
        coVerify(exactly = 1) { repository.getCharacterList() }
        assert(response.first().isEmpty())
    }

    @Test
    fun `when the repository return anything then get values from database`() = runBlocking {
        //Given
        coEvery { repository.getCharacterList() } returns flow { emit(fillMockDataDragonList().characters.map { it.toBo() }) }

        //When
         val response = useCase()

        //Then
        coVerify(exactly = 1) { repository.getCharacterList() }
        assert(response.first().isNotEmpty())
    }

}