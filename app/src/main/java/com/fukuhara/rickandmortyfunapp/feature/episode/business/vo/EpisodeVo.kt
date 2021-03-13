package com.fukuhara.rickandmortyfunapp.feature.episode.business.vo

import com.google.gson.annotations.SerializedName

data class EpisodeVo(
    @SerializedName("info") val info: EpisodeInfoVo,
    @SerializedName("results") val results: List<EpisodeResultVo>
)

data class EpisodeInfoVo(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
)

data class EpisodeResultVo(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val air_date: String,
    @SerializedName("episode") val episode: String,
    @SerializedName("characters") val characters: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
)