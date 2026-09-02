package com.example.dragonballwiki.dragonlist.ui.viewmodel

import com.example.dragonballwiki.R
import com.example.dragonballwiki.core.async.AsyncError
import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.dragonlist.domain.model.CharacterBO
import com.example.dragonballwiki.dragonlist.domain.usecase.AddCharactersLocalDataBaseUseCase
import com.example.dragonballwiki.dragonlist.domain.usecase.GetCharacterListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests unitarios para DragonListViewModel
 * 
 * Casos probados:
 * - Carga exitosa de personajes
 * - Lista vacía con fetch desde remoto exitoso
 * - Lista vacía con error de servicio
 * - Búsqueda de personajes (filtrado)
 * - Reload de datos
 * - Mapeo correcto de datos BO → VO
 */
@ExperimentalCoroutinesApi
class DragonListViewModelTest {

    private lateinit var getCharacterListUseCase: GetCharacterListUseCase
    private lateinit var addCharactersLocalDataBaseUseCase: AddCharactersLocalDataBaseUseCase
    private lateinit var viewModel: DragonListViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCharacterListUseCase = mockk()
        addCharactersLocalDataBaseUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createTestCharacterBO(
        id: Int = 1,
        name: String = "Goku",
        affiliation: String = "Z Fighter",
        description: String = "Saiyan warrior",
        gender: String = "Male",
        image: String = "https://example.com/goku.jpg",
        ki: String = "60.000.000",
        maxKi: String = "90 Septillion",
        race: String = "Saiyan"
    ) = CharacterBO(
        affiliation = affiliation,
        description = description,
        gender = gender,
        id = id,
        image = image,
        ki = ki,
        maxKi = maxKi,
        name = name,
        race = race
    )

    private fun createTestCharacterList() = listOf(
        createTestCharacterBO(id = 1, name = "Goku"),
        createTestCharacterBO(id = 2, name = "Vegeta", description = "Prince of Saiyans"),
        createTestCharacterBO(id = 3, name = "Piccolo", race = "Namekian"),
        createTestCharacterBO(id = 4, name = "Gohan", description = "Goku's son")
    )

    @Test
    fun `given successful response when viewmodel initializes then state contains character list`() = runTest(testDispatcher) {
        // Given
        val characterList = createTestCharacterList()
        coEvery { getCharacterListUseCase() } returns flowOf(characterList)
        
        // When
        viewModel = DragonListViewModel(
            getCharacterListUseCase,
            addCharactersLocalDataBaseUseCase,
            testDispatcher
        )

        // Then
        assertNotNull(viewModel.state.dragonListState)
        assertEquals(4, viewModel.state.dragonListState?.size)
        assertFalse(viewModel.state.loading)
        assertNull(viewModel.state.error)
        
        val firstCharacter = viewModel.state.dragonListState?.first()
        assertEquals("Goku", firstCharacter?.name)
        assertEquals(1, firstCharacter?.id)
    }

    @Test
    fun `given empty list and successful remote fetch when viewmodel initializes then fetches from remote`() = runTest(testDispatcher) {
        // Given
        coEvery { getCharacterListUseCase() } returns flowOf(emptyList())
        coEvery { addCharactersLocalDataBaseUseCase() } returns flowOf(AsyncResult.Success(Unit))
        
        // When
        viewModel = DragonListViewModel(
            getCharacterListUseCase,
            addCharactersLocalDataBaseUseCase,
            testDispatcher
        )

        // Then - Should show loading or wait for Room to update
        assertNull(viewModel.state.dragonListState)
    }

    @Test
    fun `given empty list and remote error when viewmodel initializes then shows error`() = runTest(testDispatcher) {
        // Given
        coEvery { getCharacterListUseCase() } returns flowOf(emptyList())
        coEvery { addCharactersLocalDataBaseUseCase() } returns flowOf(
            AsyncResult.Error(AsyncError.ConnectionError)
        )
        
        // When
        viewModel = DragonListViewModel(
            getCharacterListUseCase,
            addCharactersLocalDataBaseUseCase,
            testDispatcher
        )

        // Then
        assertNull(viewModel.state.dragonListState)
        assertFalse(viewModel.state.loading)
        assertEquals(R.string.text_error_service, viewModel.state.error)
    }

    @Test
    fun `given character name when searchCharacter then filters list correctly`() = runTest(testDispatcher) {
        // Given
        val characterList = createTestCharacterList()
        coEvery { getCharacterListUseCase() } returns flowOf(characterList)
        
        viewModel = DragonListViewModel(
            getCharacterListUseCase,
            addCharactersLocalDataBaseUseCase,
            testDispatcher
        )

        // When
        viewModel.searchCharacter("Goku")

        // Then
        assertNotNull(viewModel.state.dragonListState)
        assertEquals(1, viewModel.state.dragonListState?.size)
        assertEquals("Goku", viewModel.state.dragonListState?.first()?.name)
    }

    @Test
    fun `given character name with different case when searchCharacter then filters case insensitive`() = runTest(testDispatcher) {
        // Given
        val characterList = createTestCharacterList()
        coEvery { getCharacterListUseCase() } returns flowOf(characterList)
        
        viewModel = DragonListViewModel(
            getCharacterListUseCase,
            addCharactersLocalDataBaseUseCase,
            testDispatcher
        )

        // When
        viewModel.searchCharacter("goku")

        // Then
        assertEquals(1, viewModel.state.dragonListState?.size)
        assertEquals("Goku", viewModel.state.dragonListState?.first()?.name)
    }

    @Test
    fun `given partial name when searchCharacter then returns matching characters`() = runTest(testDispatcher) {
        // Given
        val characterList = createTestCharacterList()
        coEvery { getCharacterListUseCase() } returns flowOf(characterList)
        
        viewModel = DragonListViewModel(
            getCharacterListUseCase,
            addCharactersLocalDataBaseUseCase,
            testDispatcher
        )

        // When
        viewModel.searchCharacter("Go") // Matches Goku and Gohan

        // Then
        assertEquals(2, viewModel.state.dragonListState?.size)
        assertTrue(viewModel.state.dragonListState?.any { it.name == "Goku" } == true)
        assertTrue(viewModel.state.dragonListState?.any { it.name == "Gohan" } == true)
    }

    @Test
    fun `given empty search string when searchCharacter then returns full list`() = runTest(testDispatcher) {
        // Given
        val characterList = createTestCharacterList()
        coEvery { getCharacterListUseCase() } returns flowOf(characterList)
        
        viewModel = DragonListViewModel(
            getCharacterListUseCase,
            addCharactersLocalDataBaseUseCase,
            testDispatcher
        )

        viewModel.searchCharacter("Goku") // First filter to one

        // When
        viewModel.searchCharacter("")

        // Then
        assertEquals(4, viewModel.state.dragonListState?.size)
    }

    @Test
    fun `given blank search string when searchCharacter then returns full list`() = runTest(testDispatcher) {
        // Given
        val characterList = createTestCharacterList()
        coEvery { getCharacterListUseCase() } returns flowOf(characterList)
        
        viewModel = DragonListViewModel(
            getCharacterListUseCase,
            addCharactersLocalDataBaseUseCase,
            testDispatcher
        )

        viewModel.searchCharacter("Vegeta")

        // When
        viewModel.searchCharacter("   ")

        // Then
        assertEquals(4, viewModel.state.dragonListState?.size)
    }

    @Test
    fun `given no matching character when searchCharacter then returns empty filtered list`() = runTest(testDispatcher) {
        // Given
        val characterList = createTestCharacterList()
        coEvery { getCharacterListUseCase() } returns flowOf(characterList)
        
        viewModel = DragonListViewModel(
            getCharacterListUseCase,
            addCharactersLocalDataBaseUseCase,
            testDispatcher
        )

        // When
        viewModel.searchCharacter("NonExistentCharacter")

        // Then
        assertNotNull(viewModel.state.dragonListState)
        assertEquals(0, viewModel.state.dragonListState?.size)
    }

    @Test
    fun `given character list when mapping then VO matches BO data`() = runTest(testDispatcher) {
        // Given
        val characterBO = createTestCharacterBO(
            id = 999,
            name = "Test Character",
            affiliation = "Test Affiliation",
            description = "Test Description",
            gender = "Other",
            image = "https://test.com/image.jpg",
            ki = "1.000.000",
            maxKi = "10.000.000",
            race = "Test Race"
        )
        coEvery { getCharacterListUseCase() } returns flowOf(listOf(characterBO))
        
        // When
        viewModel = DragonListViewModel(
            getCharacterListUseCase,
            addCharactersLocalDataBaseUseCase,
            testDispatcher
        )

        // Then
        val characterVO = viewModel.state.dragonListState?.first()
        assertNotNull(characterVO)
        assertEquals(999, characterVO?.id)
        assertEquals("Test Character", characterVO?.name)
        assertEquals("Test Affiliation", characterVO?.affiliation)
        assertEquals("Test Description", characterVO?.description)
        assertEquals("Other", characterVO?.gender)
        assertEquals("https://test.com/image.jpg", characterVO?.image)
        assertEquals("1.000.000", characterVO?.ki)
        assertEquals("10.000.000", characterVO?.maxKi)
        assertEquals("Test Race", characterVO?.race)
    }

    @Test
    fun `given empty character list when reloadCharacterData then triggers remote fetch`() = runTest(testDispatcher) {
        // Given
        coEvery { getCharacterListUseCase() } returns flowOf(emptyList())
        coEvery { addCharactersLocalDataBaseUseCase() } returns flowOf(AsyncResult.Loading())
        
        viewModel = DragonListViewModel(
            getCharacterListUseCase,
            addCharactersLocalDataBaseUseCase,
            testDispatcher
        )

        // When
        viewModel.reloadCharacterData()

        // Then
        assertTrue(viewModel.state.loading)
    }
}
