package com.fukuhara.rickandmortyfunapp.feature.character.business

data class CharacterModel(
    val info: CharacterInfoModel,
    val results: List<CharacterResultModel>
)

data class CharacterInfoModel(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class CharacterResultModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val url: String,
    val create: String,
    val episode: List<String>,
    val origin: CharacterLocationModel,
    val location: CharacterLocationModel
)

data class CharacterLocationModel(
    val name: String,
    val url: String
)