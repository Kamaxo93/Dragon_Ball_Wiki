package com.example.dragonballwiki.dragonlist.data.local.datasource

import com.example.dragonballwiki.dragonlist.data.local.CharacterListDao
import com.example.dragonballwiki.dragonlist.data.remote.mapper.toBo
import com.example.dragonballwiki.dragonlist.data.remote.mapper.toEntity
import com.example.dragonballwiki.dragonlist.domain.model.toEntity
import com.example.dragonballwiki.mockdata.fillMockDataDragonList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DragonListLocalDataSourceTest {

    private val dao: CharacterListDao = mockk()

    private lateinit var dataSource: DragonListLocalDataSource

    @Before
    fun onBefore() {
        dataSource = DragonListLocalDataSourceImpl(dao)
    }

    @Test
    fun `when the database call to getCharacterList returns a list for characters`() = runTest {
        //Given
        coEvery { dao.getCharacterList() } returns flowOf(fillMockDataDragonList().characters.map { it.toBo() }.toEntity())

        //when
        val result = dataSource.getCharactersList().first()

        //Then
        coVerify(exactly = 1) {
            dao.getCharacterList()
        }

        assert(result == fillMockDataDragonList().characters.map { it.toBo() }.toEntity())
    }

    @Test
    fun `when the database call to getCharacterList returns a empty list`() = runTest {
        //Given
        coEvery { dao.getCharacterList() } returns flowOf(emptyList())

        //when
        val result = dataSource.getCharactersList().first()

        //Then
        coVerify(exactly = 1) {
            dao.getCharacterList()
        }

        assert(result.isEmpty())
    }

    @Test
    fun `when the database call to get`() = runTest {
        //Given
        coEvery { dao.getCharacter("1") } returns flowOf(fillMockDataDragonList().characters.first().toBo().toEntity())

        //when
        val result = dataSource.getCharacter("1").first()

        //Then
        coVerify(exactly = 1) {
            dao.getCharacter("1")
        }

        assert(result == fillMockDataDragonList().characters.first().toBo().toEntity())

    }

}