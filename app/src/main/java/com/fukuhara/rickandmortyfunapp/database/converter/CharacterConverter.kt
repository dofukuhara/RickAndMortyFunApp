package com.fukuhara.rickandmortyfunapp.database.converter

import androidx.room.TypeConverter
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterInfoModel
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterLocationModel
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterResultModel
import com.google.gson.Gson

class CharacterConverter {

    @TypeConverter
    fun fromCharacterResultModelList(list: List<CharacterResultModel>): String = Gson().toJson(list)

    @TypeConverter
    fun fromJsonToEpisodeCharacterModelList(value: String): List<CharacterResultModel> = Gson().fromJson(value, Array<CharacterResultModel>::class.java).toList()

    @TypeConverter
    fun fromCharacterInfoModelToJson(characterInfoModel: CharacterInfoModel): String = Gson().toJson(characterInfoModel)

    @TypeConverter
    fun fromJsonToCharacterInfoModel(value: String): CharacterInfoModel = Gson().fromJson(value, CharacterInfoModel::class.java)

}