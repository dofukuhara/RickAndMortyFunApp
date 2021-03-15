package com.fukuhara.rickandmortyfunapp.feature.episode.business.vo

import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeModel
import com.fukuhara.rickandmortyfunapp.testutils.Parser
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class EpisodeModelMapperTest {
    
    private lateinit var episodeModelMapper: ModelMapper<EpisodeVo, EpisodeModel>
    
    @Before
    fun setup() {
        episodeModelMapper = EpisodeModelMapper()
    }

    @Test
    fun `given a middle page json with only one result element, When using mapper to convert Vo into Model, Then Model should be properly generated`() {
        val pageIndex = "2"
        val episodeVoRef = Parser.jsonToObject(
            this as Any, "json/episode_middle_page_single_entry.json", EpisodeVo::class.java
        )
        val episodeModelOutput = episodeModelMapper.transform(episodeVoRef, pageIndex)

        // Attributes created by Mapper
        assertThat("PageInfo must be properly created in Mapper", episodeModelOutput.pageIndex, Is.`is`("2"))
        assertThat("Previous PageIndex must be properly created in Mapper", episodeModelOutput.info.prev, Is.`is`("1"))
        assertThat("Next PageIndex must be properly created in Mapper", episodeModelOutput.info.next, Is.`is`("3"))

        // Attributes copied by Mapper
        assertThat("Element Count must be as same as in VO", episodeModelOutput.info.count, Is.`is`(episodeVoRef.info.count))
        assertThat("Page Count must be as same as in VO", episodeModelOutput.info.pages, Is.`is`(episodeVoRef.info.pages))
        assertThat("Result array size must be as same as in VO", episodeModelOutput.results.size, Is.`is`(episodeVoRef.results.size))
        assertThat("Result Element id must be as same as in VO", episodeModelOutput.results[0].id, Is.`is`(episodeVoRef.results[0].id))
        assertThat("Result Element name must be as same as in VO", episodeModelOutput.results[0].name, Is.`is`(episodeVoRef.results[0].name))
        assertThat("Result Element air_date must be as same as in VO", episodeModelOutput.results[0].air_date, Is.`is`(episodeVoRef.results[0].air_date))
        assertThat("Result Element episode must be as same as in VO", episodeModelOutput.results[0].episode, Is.`is`(episodeVoRef.results[0].episode))
        assertThat("Result Element characters must be as same as in VO", episodeModelOutput.results[0].characters, Is.`is`(episodeVoRef.results[0].characters))
        assertThat("Result Element url must be as same as in VO", episodeModelOutput.results[0].url, Is.`is`(episodeVoRef.results[0].url))
        assertThat("Result Element created must be as same as in VO", episodeModelOutput.results[0].created, Is.`is`(episodeVoRef.results[0].created))
    }

    @Test
    fun `given a first page json, When using mapper to convert Vo into Model, Then Model should be properly generated`() {
        val pageIndex = "1"
        val episodeVoRef = Parser.jsonToObject(
            this as Any, "json/episode_page_one.json", EpisodeVo::class.java
        )
        val episodeModelOutput = episodeModelMapper.transform(episodeVoRef, pageIndex)

        // Attributes created by Mapper
        assertThat("PageInfo must be properly created in Mapper", episodeModelOutput.pageIndex, Is.`is`("1"))
        assertNull("Previous PageIndex must be null, as it is in the first page", episodeModelOutput.info.prev)
        assertThat("Next PageIndex must be properly created in Mapper", episodeModelOutput.info.next, Is.`is`("2"))
    }

    @Test
    fun `given a last page json, When using mapper to convert Vo into Model, Then Model should be properly generated`() {
        val pageIndex = "3"
        val episodeVoRef = Parser.jsonToObject(
            this as Any, "json/episode_page_last.json", EpisodeVo::class.java
        )
        val episodeModelOutput = episodeModelMapper.transform(episodeVoRef, pageIndex)

        // Attributes created by Mapper
        assertThat("PageInfo must be properly created in Mapper", episodeModelOutput.pageIndex, Is.`is`("3"))
        assertThat("Previous PageIndex must be properly created in Mapper", episodeModelOutput.info.prev, Is.`is`("2"))
        assertNull("Next PageIndex must be null, as it is in the last page", episodeModelOutput.info.next)
    }
}