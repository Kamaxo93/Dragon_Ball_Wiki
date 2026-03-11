package com.example.dragonballwiki.charactersdetail.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailErrorUseCase
import com.example.dragonballwiki.charactersdetail.domain.usecase.GetCharacterDetailUseCase
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.charactersdetail.ui.model.Transformation
import com.example.dragonballwiki.core.Constant.CHARACTER_ID
import com.example.dragonballwiki.core.async.AsyncError
import com.example.dragonballwiki.core.async.AsyncResult
import com.example.dragonballwiki.dragonlist.data.local.model.CharacterEntity
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests unitarios para CharacterDetailViewModel
 * 
 * Casos probados:
 * - Carga exitosa de detalle desde remoto
 * - Error de conexión con fallback a local database
 * - Validación de ID desde SavedStateHandle
 * - Mapeo correcto de transformaciones
 * - Estados de loading
 */
@ExperimentalCoroutinesApi
class CharacterDetailViewModelTest {

    private lateinit var getCharacterDetailUseCase: GetCharacterDetailUseCase
    private lateinit var getCharacterDetailErrorUseCase: GetCharacterDetailErrorUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: CharacterDetailViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        getCharacterDetailUseCase = mockk()
        getCharacterDetailErrorUseCase = mockk()
        savedStateHandle = mockk()
    }

    private fun createTestTransformation(
        id: Int = 1,
        name: String = "Super Saiyan",
        image: String = "https://example.com/ssj.jpg",
        ki: String = "100 Quintillion"
    ) = Transformation(
        id = id,
        name = name,
        image = image,
        ki = ki
    )

    private fun createTestCharacterDetailVO(
        id: Int = 1,
        name: String = "Goku",
        affiliation: String = "Z Fighter",
        description: String = "Saiyan warrior raised on Earth",
        gender: String = "Male",
        image: String = "https://example.com/goku.jpg",
        ki: String = "60.000.000",
        maxKi: String = "90 Septillion",
        race: String = "Saiyan",
        transformations: List<Transformation> = emptyList()
    ) = CharacterDetailVO(
        affiliation = affiliation,
        description = description,
        gender = gender,
        id = id,
        image = image,
        ki = ki,
        maxKi = maxKi,
        name = name,
        race = race,
        transformations = transformations
    )

    private fun createTestCharacterEntity(
        id: Int = 1,
        name: String = "Goku",
        affiliation: String = "Z Fighter",
        description: String = "Saiyan warrior",
        gender: String = "Male",
        image: String = "https://example.com/goku.jpg",
        ki: String = "60.000.000",
        maxKi: String = "90 Septillion",
        race: String = "Saiyan"
    ) = CharacterEntity(
        id = id,
        affiliation = affiliation,
        description = description,
        gender = gender,
        image = image,
        ki = ki,
        maxKi = maxKi,
        name = name,
        race = race
    )

    @Test
    fun `given valid character ID when viewmodel initializes then loads character detail successfully`() = runTest(testDispatcher) {
        // Given
        val characterId = "1"
        val transformations = listOf(
            createTestTransformation(id = 1, name = "Super Saiyan"),
            createTestTransformation(id = 2, name = "Super Saiyan 2")
        )
        val characterDetail = createTestCharacterDetailVO(
            id = 1,
            name = "Goku",
            transformations = transformations
        )

        every { savedStateHandle.get<String>(CHARACTER_ID) } returns characterId
        coEvery { getCharacterDetailUseCase(characterId) } returns flowOf(
            AsyncResult.Loading(),
            AsyncResult.Success(characterDetail)
        )

        // When
        viewModel = CharacterDetailViewModel(
            getCharacterDetailUseCase,
            getCharacterDetailErrorUseCase,
            testDispatcher,
            savedStateHandle
        )

        // Then
        assertNotNull(viewModel.state.characterDetail)
        assertEquals("Goku", viewModel.state.characterDetail?.name)
        assertEquals(1, viewModel.state.characterDetail?.id)
        assertEquals(2, viewModel.state.characterDetail?.transformations?.size)
        assertFalse(viewModel.state.loading)
        assertNull(viewModel.state.error)
    }

    @Test
    fun `given connection error when loading detail then falls back to local database`() = runTest(testDispatcher) {
        // Given
        val characterId = "1"
        val localEntity = createTestCharacterEntity(id = 1, name = "Goku Local")

        every { savedStateHandle.get<String>(CHARACTER_ID) } returns characterId
        coEvery { getCharacterDetailUseCase(characterId) } returns flowOf(
            AsyncResult.Error(AsyncError.ConnectionError)
        )
        coEvery { getCharacterDetailErrorUseCase(characterId) } returns flowOf(localEntity)

        // When
        viewModel = CharacterDetailViewModel(
            getCharacterDetailUseCase,
            getCharacterDetailErrorUseCase,
            testDispatcher,
            savedStateHandle
        )

        // Then
        assertNotNull(viewModel.state.characterDetail)
        assertEquals("Goku Local", viewModel.state.characterDetail?.name)
        assertEquals(1, viewModel.state.characterDetail?.id)
        assertEquals(0, viewModel.state.characterDetail?.transformations?.size) // Local entity has no transformations
        assertFalse(viewModel.state.loading)
        assertNull(viewModel.state.error)
    }

    @Test
    fun `given server error when loading detail then falls back to local database`() = runTest(testDispatcher) {
        // Given
        val characterId = "2"
        val localEntity = createTestCharacterEntity(id = 2, name = "Vegeta")

        every { savedStateHandle.get<String>(CHARACTER_ID) } returns characterId
        coEvery { getCharacterDetailUseCase(characterId) } returns flowOf(
            AsyncResult.Loading(),
            AsyncResult.Error(AsyncError.ServerError(500, "Internal Server Error"))
        )
        coEvery { getCharacterDetailErrorUseCase(characterId) } returns flowOf(localEntity)

        // When
        viewModel = CharacterDetailViewModel(
            getCharacterDetailUseCase,
            getCharacterDetailErrorUseCase,
            testDispatcher,
            savedStateHandle
        )

        // Then
        assertNotNull(viewModel.state.characterDetail)
        assertEquals("Vegeta", viewModel.state.characterDetail?.name)
        assertFalse(viewModel.state.loading)
    }

    @Test
    fun `given timeout error when loading detail then falls back to local database`() = runTest(testDispatcher) {
        // Given
        val characterId = "3"
        val localEntity = createTestCharacterEntity(id = 3, name = "Piccolo")

        every { savedStateHandle.get<String>(CHARACTER_ID) } returns characterId
        coEvery { getCharacterDetailUseCase(characterId) } returns flowOf(
            AsyncResult.Loading(),
            AsyncResult.Error(AsyncError.TimeoutError)
        )
        coEvery { getCharacterDetailErrorUseCase(characterId) } returns flowOf(localEntity)

        // When
        viewModel = CharacterDetailViewModel(
            getCharacterDetailUseCase,
            getCharacterDetailErrorUseCase,
            testDispatcher,
            savedStateHandle
        )

        // Then
        assertNotNull(viewModel.state.characterDetail)
        assertEquals("Piccolo", viewModel.state.characterDetail?.name)
    }

    @Test
    fun `given data parse error when loading detail then falls back to local database`() = runTest(testDispatcher) {
        // Given
        val characterId = "4"
        val localEntity = createTestCharacterEntity(id = 4, name = "Gohan")

        every { savedStateHandle.get<String>(CHARACTER_ID) } returns characterId
        coEvery { getCharacterDetailUseCase(characterId) } returns flowOf(
            AsyncResult.Loading(),
            AsyncResult.Error(AsyncError.DataParseError)
        )
        coEvery { getCharacterDetailErrorUseCase(characterId) } returns flowOf(localEntity)

        // When
        viewModel = CharacterDetailViewModel(
            getCharacterDetailUseCase,
            getCharacterDetailErrorUseCase,
            testDispatcher,
            savedStateHandle
        )

        // Then
        assertNotNull(viewModel.state.characterDetail)
        assertEquals("Gohan", viewModel.state.characterDetail?.name)
    }

    @Test
    fun `given empty ID when viewmodel initializes then passes empty string to use case`() = runTest(testDispatcher) {
        // Given
        val localEntity = createTestCharacterEntity(id = 0, name = "Unknown")

        every { savedStateHandle.get<String>(CHARACTER_ID) } returns null
        coEvery { getCharacterDetailUseCase("") } returns flowOf(
            AsyncResult.Loading(),
            AsyncResult.Error(AsyncError.EmptyResponseError)
        )
        coEvery { getCharacterDetailErrorUseCase("") } returns flowOf(localEntity)

        // When
        viewModel = CharacterDetailViewModel(
            getCharacterDetailUseCase,
            getCharacterDetailErrorUseCase,
            testDispatcher,
            savedStateHandle
        )

        // Then
        assertNull(viewModel.id) // null from SavedStateHandle
        assertNotNull(viewModel.state.characterDetail)
    }

    @Test
    fun `given character with transformations when loaded then transformations are mapped correctly`() = runTest(testDispatcher) {
        // Given
        val characterId = "1"
        val transformations = listOf(
            createTestTransformation(
                id = 1,
                name = "Super Saiyan",
                ki = "150 Million",
                image = "https://example.com/ssj.jpg"
            ),
            createTestTransformation(
                id = 2,
                name = "Super Saiyan 2",
                ki = "300 Million",
                image = "https://example.com/ssj2.jpg"
            ),
            createTestTransformation(
                id = 3,
                name = "Super Saiyan 3",
                ki = "600 Million",
                image = "https://example.com/ssj3.jpg"
            )
        )
        val characterDetail = createTestCharacterDetailVO(
            id = 1,
            name = "Goku",
            transformations = transformations
        )

        every { savedStateHandle.get<String>(CHARACTER_ID) } returns characterId
        coEvery { getCharacterDetailUseCase(characterId) } returns flowOf(
            AsyncResult.Loading(),
            AsyncResult.Success(characterDetail)
        )

        // When
        viewModel = CharacterDetailViewModel(
            getCharacterDetailUseCase,
            getCharacterDetailErrorUseCase,
            testDispatcher,
            savedStateHandle
        )

        // Then
        val transformationsVO = viewModel.state.characterDetail?.transformations
        assertNotNull(transformationsVO)
        assertEquals(3, transformationsVO?.size)
        assertEquals("Super Saiyan", transformationsVO?.get(0)?.name)
        assertEquals("150 Million", transformationsVO?.get(0)?.ki)
        assertEquals("Super Saiyan 3", transformationsVO?.get(2)?.name)
    }

    @Test
    fun `given character without transformations when loaded then transformations list is empty`() = runTest(testDispatcher) {
        // Given
        val characterId = "5"
        val characterDetail = createTestCharacterDetailVO(
            id = 5,
            name = "Krillin",
            transformations = emptyList()
        )

        every { savedStateHandle.get<String>(CHARACTER_ID) } returns characterId
        coEvery { getCharacterDetailUseCase(characterId) } returns flowOf(
            AsyncResult.Loading(),
            AsyncResult.Success(characterDetail)
        )

        // When
        viewModel = CharacterDetailViewModel(
            getCharacterDetailUseCase,
            getCharacterDetailErrorUseCase,
            testDispatcher,
            savedStateHandle
        )

        // Then
        assertNotNull(viewModel.state.characterDetail)
        assertEquals("Krillin", viewModel.state.characterDetail?.name)
        assertTrue(viewModel.state.characterDetail?.transformations?.isEmpty() == true)
    }

    @Test
    fun `given character detail when mapping then all fields are mapped correctly`() = runTest(testDispatcher) {
        // Given
        val characterId = "999"
        val characterDetail = createTestCharacterDetailVO(
            id = 999,
            name = "Test Character",
            affiliation = "Test Affiliation",
            description = "Test Description",
            gender = "Other",
            image = "https://test.com/image.jpg",
            ki = "1.000.000",
            maxKi = "10.000.000",
            race = "Test Race",
            transformations = emptyList()
        )

        every { savedStateHandle.get<String>(CHARACTER_ID) } returns characterId
        coEvery { getCharacterDetailUseCase(characterId) } returns flowOf(
            AsyncResult.Loading(),
            AsyncResult.Success(characterDetail)
        )

        // When
        viewModel = CharacterDetailViewModel(
            getCharacterDetailUseCase,
            getCharacterDetailErrorUseCase,
            testDispatcher,
            savedStateHandle
        )

        // Then
        val vo = viewModel.state.characterDetail
        assertNotNull(vo)
        assertEquals(999, vo?.id)
        assertEquals("Test Character", vo?.name)
        assertEquals("Test Affiliation", vo?.affiliation)
        assertEquals("Test Description", vo?.description)
        assertEquals("Other", vo?.gender)
        assertEquals("https://test.com/image.jpg", vo?.image)
        assertEquals("1.000.000", vo?.ki)
        assertEquals("10.000.000", vo?.maxKi)
        assertEquals("Test Race", vo?.race)
    }

    @Test
    fun `given local character entity when mapped then has no transformations`() = runTest(testDispatcher) {
        // Given
        val characterId = "1"
        val localEntity = createTestCharacterEntity(
            id = 1,
            name = "Local Goku",
            ki = "50.000.000",
            maxKi = "80 Septillion"
        )

        every { savedStateHandle.get<String>(CHARACTER_ID) } returns characterId
        coEvery { getCharacterDetailUseCase(characterId) } returns flowOf(
            AsyncResult.Loading(),
            AsyncResult.Error(AsyncError.ConnectionError)
        )
        coEvery { getCharacterDetailErrorUseCase(characterId) } returns flowOf(localEntity)

        // When
        viewModel = CharacterDetailViewModel(
            getCharacterDetailUseCase,
            getCharacterDetailErrorUseCase,
            testDispatcher,
            savedStateHandle
        )

        // Then
        val vo = viewModel.state.characterDetail
        assertNotNull(vo)
        assertEquals("Local Goku", vo?.name)
        assertEquals("50.000.000", vo?.ki)
        assertEquals("80 Septillion", vo?.maxKi)
        assertTrue(vo?.transformations?.isEmpty() == true)
    }
}
