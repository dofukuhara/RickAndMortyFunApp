package com.fukuhara.rickandmortyfunapp.database.converter

import androidx.room.TypeConverter
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationInfoModel
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationResultModel
import com.google.gson.Gson

class LocationConverter {

    @TypeConverter
    fun fromLocationResultModelList(list: List<LocationResultModel>): String = Gson().toJson(list)

    @TypeConverter
    fun fromJsonToLocationResultModelList(value: String): List<LocationResultModel> = Gson().fromJson(value, Array<LocationResultModel>::class.java).toList()

    @TypeConverter
    fun fromLocationInfoModelToJson(locationInfoModel: LocationInfoModel): String = Gson().toJson(locationInfoModel)

    @TypeConverter
    fun fromJsonToLocationInfoModel(value: String): LocationInfoModel = Gson().fromJson(value, LocationInfoModel::class.java)
}