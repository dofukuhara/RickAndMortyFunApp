package com.fukuhara.rickandmortyfunapp.feature.episode.business

import com.fukuhara.common.arch.Either
import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.common.arch.left
import com.fukuhara.common.arch.right
import com.fukuhara.common.exception.NoDataFound
import com.fukuhara.network.NetworkErrorHandler
import com.fukuhara.rickandmortyfunapp.feature.episode.business.vo.EpisodeVo

class EpisodeRepositoryImpl(
    private val api: EpisodeApi,
    private val dao: EpisodeDao,
    private val modelMapper: ModelMapper<EpisodeVo, EpisodeModel>,
    private val networkErrorHandler: NetworkErrorHandler
): EpisodeRepository {

    override suspend fun getData(pageIndex: String): Either<NoDataFound, EpisodeModel> {
        return try {
            val dataFromDb = dao.getData(pageIndex)

            if (dataFromDb != null) {
                dataFromDb.right()
            } else {
                val dataFromRest = modelMapper.transform(api.getEpisode(pageIndex), pageIndex)

                dao.insert(dataFromRest)

                dataFromRest.right()
            }
        } catch (throwable: Throwable) {
            networkErrorHandler.generateNoDataFoundCustomException(throwable, "Episode").left()
        }
    }
}