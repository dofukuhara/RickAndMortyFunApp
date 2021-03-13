package com.fukuhara.rickandmortyfunapp.feature.episode.business

import com.fukuhara.rickandmortyfunapp.feature.episode.business.vo.EpisodeVo
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodeApi {

    @GET("episode")
    suspend fun getEpisode(@Query("page") pageIndex: String): EpisodeVo
}