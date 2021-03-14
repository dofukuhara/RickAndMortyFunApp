package com.fukuhara.rickandmortyfunapp.feature.location.business

import com.fukuhara.common.arch.Either
import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.common.arch.left
import com.fukuhara.common.arch.right
import com.fukuhara.common.exception.NoDataFound
import com.fukuhara.network.NetworkErrorHandler
import com.fukuhara.rickandmortyfunapp.feature.location.business.vo.LocationVo

class LocationRepositoryImpl(
    private val api: LocationApi,
    private val modelMapper: ModelMapper<LocationVo, LocationModel>,
    private val networkErrorHandler: NetworkErrorHandler
) : LocationRepository {

    override suspend fun getData(pageIndex: String): Either<NoDataFound, LocationModel> {
        return try {
            modelMapper.transform(api.getData(pageIndex)).right()
        } catch (throwable: Throwable) {
            networkErrorHandler.generateNoDataFoundCustomException(throwable, "Location").left()
        }
    }
}