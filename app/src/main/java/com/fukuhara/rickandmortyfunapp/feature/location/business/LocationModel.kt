package com.fukuhara.rickandmortyfunapp.feature.location.business

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
data class LocationModel(
    @ColumnInfo(name = "info_model") val info: LocationInfoModel,
    @ColumnInfo(name = "results") val results: List<LocationResultModel>,
    @ColumnInfo(name = "page_index") val pageIndex: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)

data class LocationInfoModel(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class LocationResultModel(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)