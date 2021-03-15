package com.fukuhara.rickandmortyfunapp.feature.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fukuhara.common.arch.left
import com.fukuhara.common.arch.right
import com.fukuhara.common.exception.NoDataFound
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterRepository
import com.fukuhara.rickandmortyfunapp.feature.character.business.vo.CharacterModelMapper
import com.fukuhara.rickandmortyfunapp.feature.character.business.vo.CharacterVo
import com.fukuhara.rickandmortyfunapp.testutils.Parser
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val repository: CharacterRepository = mockk(relaxed = true)

    private lateinit var characterViewModel: CharacterViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        characterViewModel = CharacterViewModel(repository, testDispatcher)
    }

    @After
    fun tearDown() {
        unmockkAll()

        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `given a Successful Response, When Fetching First Page Of Characters Data, Then ViewModel Data Should Be Properly Set`() {
        val pageIndex = "1"
        val characterVoRef = Parser.jsonToObject(
            this as Any, "json/character_page_one.json", CharacterVo::class.java
        )
        val characterModelRef = CharacterModelMapper().transform(characterVoRef, pageIndex)

        coEvery { repository.getData("1") } returns characterModelRef.right()

        testDispatcher.runBlockingTest {
            characterViewModel.getData(pageIndex)
        }

        val characterListFromViewModel = characterViewModel.characterList.value
        assertNull("No error message must be set", characterViewModel.errorMessage.value)
        assertThat("CharacterResultList must be as same as in reference value", characterListFromViewModel, `is`(characterModelRef.results))
    }

    @Test
    fun `given a Failure Response, When Fetching First Page Of Characters Data, Then Error Message Should Be Set`() {
        val pageIndex = "1"
        val exceptionErrorMessage = "Failed to get data from service"

        coEvery { repository.getData(pageIndex) } returns NoDataFound(exceptionErrorMessage).left()

        testDispatcher.runBlockingTest {
            characterViewModel.getData(pageIndex)
        }

        val errorToBeDisplayed = characterViewModel.errorMessage.value
        assertThat("Error Message To Be Displayed in Error Feedback Screen", errorToBeDisplayed, `is`(exceptionErrorMessage))
        assertNull("No CharacterResultList must be set", characterViewModel.characterList.value)
    }

    @Test
    fun `given a Successful Response, When Fetching First Page Of Characters Data, Then Previous Button Must Be Disabled and Next Button Enabled`() {
        val pageIndex = "1"
        val nextPageIndex = "2"
        val characterVoRef = Parser.jsonToObject(
            this as Any, "json/character_page_one.json", CharacterVo::class.java
        )
        val characterModelRef = CharacterModelMapper().transform(characterVoRef, pageIndex)

        coEvery { repository.getData("1") } returns characterModelRef.right()

        testDispatcher.runBlockingTest {
            characterViewModel.getData(pageIndex)
        }

        assertNull("Previous Button must have no pageIndex set", characterViewModel.previousPage.value)
        assertFalse("Previous Button must be disabled", characterViewModel.previousPageState.value!!)
        assertThat("Next Button must have pageIndex forward set", characterViewModel.nextPage.value, `is`(nextPageIndex))
        assertTrue("Next Button must be enabled", characterViewModel.nextPageState.value!!)
    }

    @Test
    fun `given a Successful Response, When Fetching Middle Page Of Characters Data, Then Previous Button and Next Button Must Be Enabled`() {
        val pageIndex = "2"
        val previousPageIndex = "1"
        val nextPageIndex = "3"
        val characterVoRef = Parser.jsonToObject(
            this as Any, "json/character_page_two.json", CharacterVo::class.java
        )
        val characterModelRef = CharacterModelMapper().transform(characterVoRef, pageIndex)

        coEvery { repository.getData("2") } returns characterModelRef.right()

        testDispatcher.runBlockingTest {
            characterViewModel.getData(pageIndex)
        }

        assertThat("Previous Button must have pageIndex backwards set", characterViewModel.previousPage.value, `is`(previousPageIndex))
        assertTrue("Previous Button must be enabled", characterViewModel.previousPageState.value!!)
        assertThat("Next Button must have pageIndex forward set", characterViewModel.nextPage.value, `is`(nextPageIndex))
        assertTrue("Next Button must be enabled", characterViewModel.nextPageState.value!!)
    }


    @Test
    fun `given a Successful Response, When Fetching Last Page Of Characters Data, Then Previous Button Must Be Enabled and Next Button Disabled`() {
        val pageIndex = "34"
        val previousPageIndex = "33"
        val characterVoRef = Parser.jsonToObject(
            this as Any, "json/character_page_last.json", CharacterVo::class.java
        )
        val characterModelRef = CharacterModelMapper().transform(characterVoRef, pageIndex)

        coEvery { repository.getData("34") } returns characterModelRef.right()

        testDispatcher.runBlockingTest {
            characterViewModel.getData(pageIndex)
        }

        assertThat("Previous Button must have pageIndex backwards set", characterViewModel.previousPage.value, `is`(previousPageIndex))
        assertTrue("Previous Button must be enabled", characterViewModel.previousPageState.value!!)
        assertNull("Next Button must have no pageIndex set", characterViewModel.nextPage.value)
        assertFalse("Next Button must be disabled", characterViewModel.nextPageState.value!!)
    }
}