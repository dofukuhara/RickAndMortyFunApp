package com.fukuhara.rickandmortyfunapp.feature.location.business.vo

import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationModel
import com.fukuhara.rickandmortyfunapp.testutils.Parser
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class LocationModelMapperTest {
    
    private lateinit var locationModelMapper : ModelMapper<LocationVo, LocationModel>
    
    @Before
    fun setup() {
        locationModelMapper = LocationModelMapper()
    }

    @Test
    fun `given a middle page json with only one result element, When using mapper to convert Vo into Model, Then Model should be properly generated`() {
        val pageIndex = "2"
        val locationVoRef = Parser.jsonToObject(
            this as Any, "json/location_middle_page_single_entry.json", LocationVo::class.java
        )
        val locationModelOutput = locationModelMapper.transform(locationVoRef, pageIndex)

        // Attributes created by Mapper
        assertThat("PageInfo must be properly created in Mapper", locationModelOutput.pageIndex, Is.`is`("2"))
        assertThat("Previous PageIndex must be properly created in Mapper", locationModelOutput.info.prev, Is.`is`("1"))
        assertThat("Next PageIndex must be properly created in Mapper", locationModelOutput.info.next, Is.`is`("3"))

        // Attributes copied by Mapper
        assertThat("Element Count must be as same as in VO", locationModelOutput.info.count, Is.`is`(locationVoRef.info.count))
        assertThat("Page Count must be as same as in VO", locationModelOutput.info.pages, Is.`is`(locationVoRef.info.pages))
        assertThat("Result array size must be as same as in VO", locationModelOutput.results.size, Is.`is`(locationVoRef.results.size))
        assertThat("Result Element id must be as same as in VO", locationModelOutput.results[0].id, Is.`is`(locationVoRef.results[0].id))
        assertThat("Result Element name must be as same as in VO", locationModelOutput.results[0].name, Is.`is`(locationVoRef.results[0].name))
        assertThat("Result Element type must be as same as in VO", locationModelOutput.results[0].type, Is.`is`(locationVoRef.results[0].type))
        assertThat("Result Element dimension must be as same as in VO", locationModelOutput.results[0].dimension, Is.`is`(locationVoRef.results[0].dimension))
        assertThat("Result Element residents must be as same as in VO", locationModelOutput.results[0].residents, Is.`is`(locationVoRef.results[0].residents))
        assertThat("Result Element url must be as same as in VO", locationModelOutput.results[0].url, Is.`is`(locationVoRef.results[0].url))
        assertThat("Result Element created must be as same as in VO", locationModelOutput.results[0].created, Is.`is`(locationVoRef.results[0].created))
    }

    @Test
    fun `given a first page json, When using mapper to convert Vo into Model, Then Model should be properly generated`() {
        val pageIndex = "1"
        val locationVoRef = Parser.jsonToObject(
            this as Any, "json/location_page_one.json", LocationVo::class.java
        )
        val locationModelOutput = locationModelMapper.transform(locationVoRef, pageIndex)

        // Attributes created by Mapper
        assertThat("PageInfo must be properly created in Mapper", locationModelOutput.pageIndex, Is.`is`("1"))
        assertNull("Previous PageIndex must be null, as it is in the first page", locationModelOutput.info.prev)
        assertThat("Next PageIndex must be properly created in Mapper", locationModelOutput.info.next, Is.`is`("2"))
    }

    @Test
    fun `given a last page json, When using mapper to convert Vo into Model, Then Model should be properly generated`() {
        val pageIndex = "6"
        val locationVoRef = Parser.jsonToObject(
            this as Any, "json/location_page_last.json", LocationVo::class.java
        )
        val locationModelOutput = locationModelMapper.transform(locationVoRef, pageIndex)

        // Attributes created by Mapper
        assertThat("PageInfo must be properly created in Mapper", locationModelOutput.pageIndex, Is.`is`("6"))
        assertThat("Previous PageIndex must be properly created in Mapper", locationModelOutput.info.prev, Is.`is`("5"))
        assertNull("Next PageIndex must be null, as it is in the last page", locationModelOutput.info.next)
    }
}