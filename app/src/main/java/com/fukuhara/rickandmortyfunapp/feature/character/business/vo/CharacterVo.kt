package com.fukuhara.rickandmortyfunapp.feature.character.business.vo

import com.google.gson.annotations.SerializedName

data class CharacterVo(
    @SerializedName("info") val info: CharacterInfoVo,
    @SerializedName("results") val results: List<CharacterResultVo>
)

data class CharacterInfoVo(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
)

data class CharacterResultVo(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("image") val image: String,
    @SerializedName("url") val url: String,
    @SerializedName("created") val create: String,
    @SerializedName("episode") val episode: List<String>,
    @SerializedName("origin") val origin: CharacterLocationVo,
    @SerializedName("location") val location: CharacterLocationVo
)

data class CharacterLocationVo(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)