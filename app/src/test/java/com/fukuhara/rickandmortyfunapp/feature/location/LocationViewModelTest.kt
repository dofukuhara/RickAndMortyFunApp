package com.fukuhara.rickandmortyfunapp.feature.location

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fukuhara.common.arch.left
import com.fukuhara.common.arch.right
import com.fukuhara.common.exception.NoDataFound
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationRepository
import com.fukuhara.rickandmortyfunapp.feature.location.business.vo.LocationModelMapper
import com.fukuhara.rickandmortyfunapp.feature.location.business.vo.LocationVo
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
class LocationViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val repository: LocationRepository = mockk(relaxed = true)

    private lateinit var locationViewModel: LocationViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        locationViewModel = LocationViewModel(repository, testDispatcher)
    }

    @After
    fun tearDown() {
        unmockkAll()

        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `given a Successful Response, When Fetching First Page Of Location Data, Then ViewModel Data Should Be Properly Set`() {
        val pageIndex = "1"
        val locationVoRef = Parser.jsonToObject(
            this as Any, "json/location_page_one.json", LocationVo::class.java
        )
        val locationModelRef = LocationModelMapper().transform(locationVoRef, pageIndex)

        coEvery { repository.getData("1") } returns locationModelRef.right()

        testDispatcher.runBlockingTest {
            locationViewModel.getData(pageIndex)
        }

        val locationListFromViewModel = locationViewModel.locationList.value
        assertNull("No error message must be set", locationViewModel.errorMessage.value)
        assertThat(
            "LocationResultList must be as same as in reference value",
            locationListFromViewModel,
            Is.`is`(locationModelRef.results)
        )
    }

    @Test
    fun `given a Failure Response, When Fetching First Page Of Location Data, Then Error Message Should Be Set`() {
        val pageIndex = "1"
        val exceptionErrorMessage = "Failed to get data from service"

        coEvery { repository.getData(pageIndex) } returns NoDataFound(exceptionErrorMessage).left()

        testDispatcher.runBlockingTest {
            locationViewModel.getData(pageIndex)
        }

        val errorToBeDisplayed = locationViewModel.errorMessage.value
        assertThat(
            "Error Message To Be Displayed in Error Feedback Screen",
            errorToBeDisplayed,
            Is.`is`(exceptionErrorMessage)
        )
        assertNull(
            "No LocationResultList must be set",
            locationViewModel.locationList.value
        )
    }

    @Test
    fun `given a Successful Response, When Fetching First Page Of Location Data, Then Previous Button Must Be Disabled and Next Button Enabled`() {
        val pageIndex = "1"
        val nextPageIndex = "2"
        val locationVoRef = Parser.jsonToObject(
            this as Any, "json/location_page_one.json", LocationVo::class.java
        )
        val locationModelRef = LocationModelMapper().transform(locationVoRef, pageIndex)

        coEvery { repository.getData("1") } returns locationModelRef.right()

        testDispatcher.runBlockingTest {
            locationViewModel.getData(pageIndex)
        }

        assertNull(
            "Previous Button must have no pageIndex set",
            locationViewModel.previousPage.value
        )
        assertFalse(
            "Previous Button must be disabled",
            locationViewModel.previousPageState.value!!
        )
        assertThat(
            "Next Button must have pageIndex forward set",
            locationViewModel.nextPage.value,
            Is.`is`(nextPageIndex)
        )
        assertTrue("Next Button must be enabled", locationViewModel.nextPageState.value!!)
    }

    @Test
    fun `given a Successful Response, When Fetching Middle Page Of Location Data, Then Previous Button and Next Button Must Be Enabled`() {
        val pageIndex = "2"
        val previousPageIndex = "1"
        val nextPageIndex = "3"
        val locationVoRef = Parser.jsonToObject(
            this as Any, "json/location_page_two.json", LocationVo::class.java
        )
        val locationModelRef = LocationModelMapper().transform(locationVoRef, pageIndex)

        coEvery { repository.getData("2") } returns locationModelRef.right()

        testDispatcher.runBlockingTest {
            locationViewModel.getData(pageIndex)
        }

        assertThat(
            "Previous Button must have pageIndex backwards set",
            locationViewModel.previousPage.value,
            Is.`is`(previousPageIndex)
        )
        assertTrue(
            "Previous Button must be enabled",
            locationViewModel.previousPageState.value!!
        )
        assertThat(
            "Next Button must have pageIndex forward set",
            locationViewModel.nextPage.value,
            Is.`is`(nextPageIndex)
        )
        assertTrue("Next Button must be enabled", locationViewModel.nextPageState.value!!)
    }


    @Test
    fun `given a Successful Response, When Fetching Last Page Of Location Data, Then Previous Button Must Be Enabled and Next Button Disabled`() {
        val pageIndex = "6"
        val previousPageIndex = "5"
        val locationVoRef = Parser.jsonToObject(
            this as Any, "json/location_page_last.json", LocationVo::class.java
        )
        val locationModelRef = LocationModelMapper().transform(locationVoRef, pageIndex)

        coEvery { repository.getData("6") } returns locationModelRef.right()

        testDispatcher.runBlockingTest {
            locationViewModel.getData(pageIndex)
        }

        assertThat(
            "Previous Button must have pageIndex backwards set",
            locationViewModel.previousPage.value,
            Is.`is`(previousPageIndex)
        )
        assertTrue(
            "Previous Button must be enabled",
            locationViewModel.previousPageState.value!!
        )
        assertNull(
            "Next Button must have no pageIndex set",
            locationViewModel.nextPage.value
        )
        assertFalse("Next Button must be disabled", locationViewModel.nextPageState.value!!)
    }
}