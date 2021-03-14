package com.fukuhara.rickandmortyfunapp.feature.episode.business

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode_table")
data class EpisodeModel(
    @ColumnInfo(name = "info_model") val info: EpisodeInfoModel,
    @ColumnInfo(name = "results") val results: List<EpisodeResultModel>,
    @ColumnInfo(name = "page_index") val pageIndex: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
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