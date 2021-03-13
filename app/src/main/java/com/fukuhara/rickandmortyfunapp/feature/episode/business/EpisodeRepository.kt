package com.fukuhara.rickandmortyfunapp.feature.episode.business

import com.fukuhara.common.arch.Either
import com.fukuhara.common.exception.NoDataFound

interface EpisodeRepository {
    suspend fun getData(pageIndex: String): Either<NoDataFound, EpisodeModel>
}