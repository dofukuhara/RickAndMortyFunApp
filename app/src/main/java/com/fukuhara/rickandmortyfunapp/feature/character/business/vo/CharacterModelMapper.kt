package com.fukuhara.rickandmortyfunapp.feature.character.business.vo

import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.common.ext.getPageIndex
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterInfoModel
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterLocationModel
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterModel
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterResultModel

class CharacterModelMapper : ModelMapper<CharacterVo, CharacterModel> {

    override fun transform(voData: CharacterVo): CharacterModel {
        val nextIndex = voData.info.next?.getPageIndex("character")
        val previousIndex = voData.info.prev?.getPageIndex("character")

        val characterInfoModel = CharacterInfoModel(
            voData.info.count,
            voData.info.pages,
            nextIndex,
            previousIndex
        )

        val characterResultModelList = voData.results.map {
            CharacterResultModel(
                it.id, it.name, it.status, it.species, it.type, it.gender, it.image, it.url, it.create, it.episode,
                CharacterLocationModel(it.origin.name, it.origin.url),
                CharacterLocationModel(it.location.name, it.location.url)
            )
        }

        return CharacterModel(characterInfoModel, characterResultModelList)
    }
}