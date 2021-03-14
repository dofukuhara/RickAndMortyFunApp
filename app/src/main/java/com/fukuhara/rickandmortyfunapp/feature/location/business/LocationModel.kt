package com.fukuhara.rickandmortyfunapp.feature.location.business

data class LocationModel(
   val info: LocationInfoModel,
    val results: List<LocationResultModel>
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