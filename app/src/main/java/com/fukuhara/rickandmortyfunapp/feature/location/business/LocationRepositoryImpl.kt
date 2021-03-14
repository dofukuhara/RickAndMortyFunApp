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
    private val dao: LocationDao,
    private val modelMapper: ModelMapper<LocationVo, LocationModel>,
    private val networkErrorHandler: NetworkErrorHandler
) : LocationRepository {

    override suspend fun getData(pageIndex: String): Either<NoDataFound, LocationModel> {
        return try {
            val dataFromDb = dao.getData(pageIndex)

            if (dataFromDb != null) {
                dataFromDb.right()
            } else {
                val dataFromRest = modelMapper.transform(api.getData(pageIndex), pageIndex)

                dao.insert(dataFromRest)

                dataFromRest.right()
            }

        } catch (throwable: Throwable) {
            networkErrorHandler.generateNoDataFoundCustomException(throwable, "Location").left()
        }
    }
}