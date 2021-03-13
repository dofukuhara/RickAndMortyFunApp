package com.fukuhara.rickandmortyfunapp.feature.episode.business

import com.fukuhara.common.arch.Either
import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.common.arch.left
import com.fukuhara.common.arch.right
import com.fukuhara.common.exception.NoDataFound
import com.fukuhara.rickandmortyfunapp.feature.episode.business.vo.EpisodeVo

class EpisodeRepositoryImpl(
    private val api: EpisodeApi,
    private val modelMapper: ModelMapper<EpisodeVo, EpisodeModel>
): EpisodeRepository {

    override suspend fun getData(pageIndex: String): Either<NoDataFound, EpisodeModel> {
        return try {
            modelMapper.transform(api.getEpisode(pageIndex)).right()
        } catch (throwable: Throwable) {
            NoDataFound(throwable.message ?: "Failed to retrieve data from Episode API").left()
        }
    }
}