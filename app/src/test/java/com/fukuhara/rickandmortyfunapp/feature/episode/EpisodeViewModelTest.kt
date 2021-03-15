package com.fukuhara.rickandmortyfunapp.feature.episode

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fukuhara.common.arch.left
import com.fukuhara.common.arch.right
import com.fukuhara.common.exception.NoDataFound
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeRepository
import com.fukuhara.rickandmortyfunapp.feature.episode.business.vo.EpisodeModelMapper
import com.fukuhara.rickandmortyfunapp.feature.episode.business.vo.EpisodeVo
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
import org.hamcrest.core.Is
import org.junit.*
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class EpisodeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val repository: EpisodeRepository = mockk(relaxed = true)

    private lateinit var episodeViewModel: EpisodeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        episodeViewModel = EpisodeViewModel(repository, testDispatcher)
    }

    @After
    fun tearDown() {
        unmockkAll()

        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `given a Successful Response, When Fetching First Page Of Episode Data, Then ViewModel Data Should Be Properly Set`() {
        val pageIndex = "1"
        val episodeVoRef = Parser.jsonToObject(
            this as Any, "json/episode_page_one.json", EpisodeVo::class.java
        )
        val episodeModelRef = EpisodeModelMapper().transform(episodeVoRef, pageIndex)

        coEvery { repository.getData("1") } returns episodeModelRef.right()

        testDispatcher.runBlockingTest {
            episodeViewModel.getData(pageIndex)
        }

        val episodeListFromViewModel = episodeViewModel.episodeList.value
        assertNull("No error message must be set", episodeViewModel.errorMessage.value)
        assertThat(
            "EpisodeResultList must be as same as in reference value",
            episodeListFromViewModel,
            Is.`is`(episodeModelRef.results)
        )
    }

    @Test
    fun `given a Failure Response, When Fetching First Page Of Episode Data, Then Error Message Should Be Set`() {
        val pageIndex = "1"
        val exceptionErrorMessage = "Failed to get data from service"

        coEvery { repository.getData(pageIndex) } returns NoDataFound(exceptionErrorMessage).left()

        testDispatcher.runBlockingTest {
            episodeViewModel.getData(pageIndex)
        }

        val errorToBeDisplayed = episodeViewModel.errorMessage.value
        assertThat(
            "Error Message To Be Displayed in Error Feedback Screen",
            errorToBeDisplayed,
            Is.`is`(exceptionErrorMessage)
        )
        assertNull(
            "No EpisodeResultList must be set",
            episodeViewModel.episodeList.value
        )
    }

    @Test
    fun `given a Successful Response, When Fetching First Page Of Episode Data, Then Previous Button Must Be Disabled and Next Button Enabled`() {
        val pageIndex = "1"
        val nextPageIndex = "2"
        val episodeVoRef = Parser.jsonToObject(
            this as Any, "json/episode_page_one.json", EpisodeVo::class.java
        )
        val episodeModelRef = EpisodeModelMapper().transform(episodeVoRef, pageIndex)

        coEvery { repository.getData("1") } returns episodeModelRef.right()

        testDispatcher.runBlockingTest {
            episodeViewModel.getData(pageIndex)
        }

        assertNull(
            "Previous Button must have no pageIndex set",
            episodeViewModel.previousPage.value
        )
        assertFalse(
            "Previous Button must be disabled",
            episodeViewModel.previousPageState.value!!
        )
        assertThat(
            "Next Button must have pageIndex forward set",
            episodeViewModel.nextPage.value,
            Is.`is`(nextPageIndex)
        )
        assertTrue("Next Button must be enabled", episodeViewModel.nextPageState.value!!)
    }

    @Test
    fun `given a Successful Response, When Fetching Middle Page Of Episode Data, Then Previous Button and Next Button Must Be Enabled`() {
        val pageIndex = "2"
        val previousPageIndex = "1"
        val nextPageIndex = "3"
        val episodeVoRef = Parser.jsonToObject(
            this as Any, "json/episode_page_two.json", EpisodeVo::class.java
        )
        val episodeModelRef = EpisodeModelMapper().transform(episodeVoRef, pageIndex)

        coEvery { repository.getData("2") } returns episodeModelRef.right()

        testDispatcher.runBlockingTest {
            episodeViewModel.getData(pageIndex)
        }

        assertThat(
            "Previous Button must have pageIndex backwards set",
            episodeViewModel.previousPage.value,
            Is.`is`(previousPageIndex)
        )
        assertTrue(
            "Previous Button must be enabled",
            episodeViewModel.previousPageState.value!!
        )
        assertThat(
            "Next Button must have pageIndex forward set",
            episodeViewModel.nextPage.value,
            Is.`is`(nextPageIndex)
        )
        assertTrue("Next Button must be enabled", episodeViewModel.nextPageState.value!!)
    }


    @Test
    fun `given a Successful Response, When Fetching Last Page Of Episode Data, Then Previous Button Must Be Enabled and Next Button Disabled`() {
        val pageIndex = "3"
        val previousPageIndex = "2"
        val episodeVoRef = Parser.jsonToObject(
            this as Any, "json/episode_page_last.json", EpisodeVo::class.java
        )
        val episodeModelRef = EpisodeModelMapper().transform(episodeVoRef, pageIndex)

        coEvery { repository.getData("3") } returns episodeModelRef.right()

        testDispatcher.runBlockingTest {
            episodeViewModel.getData(pageIndex)
        }

        assertThat(
            "Previous Button must have pageIndex backwards set",
            episodeViewModel.previousPage.value,
            Is.`is`(previousPageIndex)
        )
        assertTrue(
            "Previous Button must be enabled",
            episodeViewModel.previousPageState.value!!
        )
        assertNull(
            "Next Button must have no pageIndex set",
            episodeViewModel.nextPage.value
        )
        assertFalse("Next Button must be disabled", episodeViewModel.nextPageState.value!!)
    }
}