package com.fukuhara.rickandmortyfunapp.feature.location.business.vo

import com.google.gson.annotations.SerializedName

data class LocationVo(
    @SerializedName("info") val info: LocationInfoVo,
    @SerializedName("results") val results: List<LocationResultVo>
)

data class LocationInfoVo(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
)

data class LocationResultVo(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("dimension") val dimension: String,
    @SerializedName("residents") val residents: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
)