package com.fukuhara.rickandmortyfunapp.feature.episode.business

data class EpisodeModel(
    val info: EpisodeInfoModel,
    val results: List<EpisodeResultModel>
)

data class EpisodeInfoModel(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class EpisodeResultModel(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)