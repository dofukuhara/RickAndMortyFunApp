package com.fukuhara.rickandmortyfunapp.feature.character.business

import com.fukuhara.common.arch.Either
import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.common.arch.left
import com.fukuhara.common.arch.right
import com.fukuhara.common.exception.NoDataFound
import com.fukuhara.network.NetworkErrorHandler
import com.fukuhara.rickandmortyfunapp.feature.character.business.vo.CharacterVo

class CharacterRepositoryImpl(
    private val api: CharacterApi,
    private val dao: CharacterDao,
    private val mapper: ModelMapper<CharacterVo, CharacterModel>,
    private val networkErrorHandler: NetworkErrorHandler
) : CharacterRepository {

    override suspend fun getData(pageIndex: String): Either<NoDataFound, CharacterModel> {
        return try {
            val dataFromDb = dao.getData(pageIndex)

            if (dataFromDb != null) {
                dataFromDb.right()
            } else {
                val dataFromRest = mapper.transform(api.getData(pageIndex), pageIndex)

                dao.insert(dataFromRest)

                dataFromRest.right()
            }

        } catch (throwable: Throwable) {
            networkErrorHandler.generateNoDataFoundCustomException(throwable, "Character").left()
        }
    }
}