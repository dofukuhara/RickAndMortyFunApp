package com.fukuhara.rickandmortyfunapp.feature.character.business

import com.fukuhara.rickandmortyfunapp.feature.character.business.vo.CharacterVo
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("character")
    suspend fun getData(@Query("page") pageIndex: String) : CharacterVo
}