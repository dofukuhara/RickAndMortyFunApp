package com.fukuhara.network

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class NetworkErrorHandlerTest {

    private lateinit var networkErrorHandler: NetworkErrorHandler

    @Before
    fun setup() {
        networkErrorHandler = NetworkErrorHandlerImpl()
    }

    @Test
    fun `given an UnknownHostException Error, When Generating Custom NoDataFound Exception, Then Use Custom Message Instead Of UnknownHostException Error Message`() {
        val exception = UnknownHostException("This is a mock UnknownHostException Exception")
        val content = "Episode"

        val noDataFoundGenerated = networkErrorHandler.generateNoDataFoundCustomException(exception, content)

        assertThat("", noDataFoundGenerated.message, `is`("Due to a network issue, it was not possible to retrieve $content data.\nPlease try again later."))
    }

    @Test
    fun `given a non UnknownHostException Error Without Error Message, When Generating Custom NoDataFound Exception, Then Use Custom Message`() {
        val exception = Throwable()
        val content = "Episode"

        val noDataFoundGenerated = networkErrorHandler.generateNoDataFoundCustomException(exception, content)

        assertThat("", noDataFoundGenerated.message, `is`("Due to a network issue, it was not possible to retrieve $content data.\nPlease try again later."))
    }

    @Test
    fun `given a non UnknownHostException Error With Error Message, When Generating Custom NoDataFound Exception, Then Use Exception Error Message`() {
        val exception = Throwable("This non UnknownHostException has a custom message")
        val content = "Episode"

        val noDataFoundGenerated = networkErrorHandler.generateNoDataFoundCustomException(exception, content)

        assertThat("", noDataFoundGenerated.message, `is`("This non UnknownHostException has a custom message"))

    }
}