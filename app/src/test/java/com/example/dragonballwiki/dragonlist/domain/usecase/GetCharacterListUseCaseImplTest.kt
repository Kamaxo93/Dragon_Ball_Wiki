package com.example.dragonballwiki.dragonlist.domain.usecase

import com.example.dragonballwiki.dragonlist.domain.repository.DragonListRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetCharacterListUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: DragonListRepository

    private lateinit var useCase: GetCharacterListUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        useCase = GetCharacterListUseCaseImpl(repository)
    }

    @Test
    fun `when the api doesnt return anything then get values from database`() = runBlocking {
        //Given

        coEvery { repository.getCharacterList() } returns flow { emit(listOf()) }

        //When
        useCase()

        //Then
        coVerify(exactly = 1) { repository.getCharacterList() }

    }

}