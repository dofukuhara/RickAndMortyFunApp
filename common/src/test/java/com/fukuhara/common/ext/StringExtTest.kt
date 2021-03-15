package com.fukuhara.common.ext

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertNull
import org.junit.Test

class StringExtTest {

    @Test
    fun `given a valid URL with valid content, When parsing page index, Then page index should be extracted and returned`() {
        assertThat(
            "PageIndex 2 must be properly extracted from URL",
            "https://rickandmortyapi.com/api/character/?page=2".getPageIndex("character"),
            `is`("2")
        )
    }

    @Test
    fun `given a valid URL with very long page index, When parsing page index, Then page index should be extracted and returned`() {
        assertThat(
            "PageIndex 2 must be properly extracted from URL",
            "https://rickandmortyapi.com/api/character/?page=9876543219876543210".getPageIndex("character"),
            `is`("9876543219876543210")
        )
    }

    @Test
    fun `given a valid URL with wrong content, When parsing page index, Then null should be returned`() {
        assertNull(
            "No PageIndex should be extracted and content param is wrong",
            "https://rickandmortyapi.com/api/character/?page=2".getPageIndex("episode")
        )
    }

    @Test
    fun `given an URL without page index, When parsing page index, Then null should be returned`() {
        assertNull(
            "No PageIndex should be extracted and content param is wrong",
            "https://rickandmortyapi.com/api/character/".getPageIndex("character")
        )
    }

    @Test
    fun `given a valid episode in season string, When parsing season and episode, Then pair value with season and episode must be returned`() {
        val seasonAndEpisode = "S01E02".getSeasonAndEpisode()

        assertThat("Season value must be properly set", seasonAndEpisode?.first, `is`("01"))
        assertThat("Episode in season value must be properly set", seasonAndEpisode?.second, `is`("02"))
    }

    @Test
    fun `given a valid episode in season string in lowercase, When parsing season and episode, Then pair value with season and episode must be returned`() {
        val seasonAndEpisode = "s01e02".getSeasonAndEpisode()

        assertThat("Season value must be properly set", seasonAndEpisode?.first, `is`("01"))
        assertThat("Episode in season value must be properly set", seasonAndEpisode?.second, `is`("02"))
    }

    @Test
    fun `give a string with only season part, When parsing season and episode, Then null must be returned`() {
        val seasonAndEpisode = "S01".getSeasonAndEpisode()

        assertNull("Return must be null as the string does not matches the pattern", seasonAndEpisode)
    }

    @Test
    fun `give a string with only episode part, When parsing season and episode, Then null must be returned`() {
        val seasonAndEpisode = "E02".getSeasonAndEpisode()

        assertNull("Return must be null as the string does not matches the pattern", seasonAndEpisode)
    }
}