package com.fukuhara.rickandmortyfunapp.feature.location.business

import com.fukuhara.common.arch.Either
import com.fukuhara.common.exception.NoDataFound

interface LocationRepository {

    suspend fun getData(pageIndex: String) : Either<NoDataFound, LocationModel>
}