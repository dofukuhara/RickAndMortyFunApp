package com.fukuhara.rickandmortyfunapp.feature.episode.business.vo

import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.common.ext.getPageIndex
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeInfoModel
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeModel
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeResultModel

class EpisodeModelMapper: ModelMapper<EpisodeVo, EpisodeModel> {
    override fun transform(voData: EpisodeVo): EpisodeModel {
        val nextIndex = voData.info.next?.getPageIndex("episode")
        val previousIndex = voData.info.prev?.getPageIndex("episode")

        val episodeInfoModel = EpisodeInfoModel(
            voData.info.count,
            voData.info.pages,
            nextIndex,
            previousIndex
        )
        val episodeResultModelList = voData.results.map {
            EpisodeResultModel(
                it.id,
                it.name,
                it.air_date,
                it.episode,
                it.characters,
                it.url,
                it.created
            )
        }

        return EpisodeModel(episodeInfoModel, episodeResultModelList)
    }
}