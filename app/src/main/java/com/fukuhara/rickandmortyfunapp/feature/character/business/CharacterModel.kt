package com.fukuhara.rickandmortyfunapp.feature.character.business

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class CharacterModel(
    @ColumnInfo(name = "info_model") val info: CharacterInfoModel,
    @ColumnInfo(name = "results") val results: List<CharacterResultModel>,
    @ColumnInfo(name = "page_index") val pageIndex: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
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