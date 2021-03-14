package com.fukuhara.rickandmortyfunapp.database.converter

import androidx.room.TypeConverter
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeInfoModel
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeResultModel
import com.google.gson.Gson

class EpisodeConverters {

    @TypeConverter
    fun fromEpisodeResultModelList(list: List<EpisodeResultModel>): String = Gson().toJson(list)

    @TypeConverter
    fun fromJsonToEpisodeResultModelList(value: String): List<EpisodeResultModel> = Gson().fromJson(value, Array<EpisodeResultModel>::class.java).toList()

    @TypeConverter
    fun fromEpisodeInfoModelToJson(episodeInfoModel: EpisodeInfoModel): String = Gson().toJson(episodeInfoModel)

    @TypeConverter
    fun fromJsonToEpisodeInfoModel(value: String): EpisodeInfoModel = Gson().fromJson(value, EpisodeInfoModel::class.java)
}