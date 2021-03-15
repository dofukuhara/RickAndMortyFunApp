package com.fukuhara.rickandmortyfunapp.feature.character.business.vo

import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterModel
import com.fukuhara.rickandmortyfunapp.testutils.Parser
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class CharacterModelMapperTest {

    private lateinit var characterModelMapper: ModelMapper<CharacterVo, CharacterModel>

    @Before
    fun setup() {
        characterModelMapper = CharacterModelMapper()
    }

    @Test
    fun `given a middle page json with only one result element, When using mapper to convert Vo into Model, Then Model should be properly generated`() {
        val pageIndex = "2"
        val characterVoRef = Parser.jsonToObject(
            this as Any, "json/character_middle_page_single_entry.json", CharacterVo::class.java
        )
        val characterModelOutput = CharacterModelMapper().transform(characterVoRef, pageIndex)

        // Attributes created by Mapper
        assertThat("PageInfo must be properly created in Mapper", characterModelOutput.pageIndex, `is`("2"))
        assertThat("Previous PageIndex must be properly created in Mapper", characterModelOutput.info.prev, `is`("1"))
        assertThat("Next PageIndex must be properly created in Mapper", characterModelOutput.info.next, `is`("3"))

        // Attributes copied by Mapper
        assertThat("Element Count must be as same as in VO", characterModelOutput.info.count, `is`(characterVoRef.info.count))
        assertThat("Page Count must be as same as in VO", characterModelOutput.info.pages, `is`(characterVoRef.info.pages))
        assertThat("Result array size must be as same as in VO", characterModelOutput.results.size, `is`(characterVoRef.results.size))
        assertThat("Result Element id must be as same as in VO", characterModelOutput.results[0].id, `is`(characterVoRef.results[0].id))
        assertThat("Result Element name must be as same as in VO", characterModelOutput.results[0].name, `is`(characterVoRef.results[0].name))
        assertThat("Result Element status must be as same as in VO", characterModelOutput.results[0].status, `is`(characterVoRef.results[0].status))
        assertThat("Result Element species must be as same as in VO", characterModelOutput.results[0].species, `is`(characterVoRef.results[0].species))
        assertThat("Result Element type must be as same as in VO", characterModelOutput.results[0].type, `is`(characterVoRef.results[0].type))
        assertThat("Result Element gender must be as same as in VO", characterModelOutput.results[0].gender, `is`(characterVoRef.results[0].gender))
        assertThat("Result Element image must be as same as in VO", characterModelOutput.results[0].image, `is`(characterVoRef.results[0].image))
        assertThat("Result Element url must be as same as in VO", characterModelOutput.results[0].url, `is`(characterVoRef.results[0].url))
        assertThat("Result Element create must be as same as in VO", characterModelOutput.results[0].create, `is`(characterVoRef.results[0].create))
        assertThat("Result Element episode must be as same as in VO", characterModelOutput.results[0].episode, `is`(characterVoRef.results[0].episode))

        // Converted CharacterLocationVo into CharacterLocationModel
        assertThat("Origin Name must be as same as in OriginVo", characterModelOutput.results[0].origin.name, `is`(characterVoRef.results[0].origin.name))
        assertThat("Origin URL must be as same as in OriginVo", characterModelOutput.results[0].origin.url, `is`(characterVoRef.results[0].origin.url))
        assertThat("Location Name must be as same as in LocationVo", characterModelOutput.results[0].location.name, `is`(characterVoRef.results[0].location.name))
        assertThat("Location URL must be as same as in LocationVo", characterModelOutput.results[0].location.url, `is`(characterVoRef.results[0].location.url))
    }

    @Test
    fun `given a first page json, When using mapper to convert Vo into Model, Then Model should be properly generated`() {
        val pageIndex = "1"
        val characterVoRef = Parser.jsonToObject(
            this as Any, "json/character_page_one.json", CharacterVo::class.java
        )
        val characterModelOutput = CharacterModelMapper().transform(characterVoRef, pageIndex)

        // Attributes created by Mapper
        assertThat("PageInfo must be properly created in Mapper", characterModelOutput.pageIndex, `is`("1"))
        assertNull("Previous PageIndex must be null, as it is in the first page", characterModelOutput.info.prev)
        assertThat("Next PageIndex must be properly created in Mapper", characterModelOutput.info.next, `is`("2"))
    }

    @Test
    fun `given a last page json, When using mapper to convert Vo into Model, Then Model should be properly generated`() {
        val pageIndex = "34"
        val characterVoRef = Parser.jsonToObject(
            this as Any, "json/character_page_last.json", CharacterVo::class.java
        )
        val characterModelOutput = CharacterModelMapper().transform(characterVoRef, pageIndex)

        // Attributes created by Mapper
        assertThat("PageInfo must be properly created in Mapper", characterModelOutput.pageIndex, `is`("34"))
        assertThat("Previous PageIndex must be properly created in Mapper", characterModelOutput.info.prev, `is`("33"))
        assertNull("Next PageIndex must be null, as it is in the last page", characterModelOutput.info.next)
    }
}