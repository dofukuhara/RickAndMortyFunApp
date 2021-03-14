package com.fukuhara.rickandmortyfunapp.feature.location.business

import com.fukuhara.rickandmortyfunapp.feature.location.business.vo.LocationVo
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET("location")
    suspend fun getData(@Query("page") pageIndex: String): LocationVo
}