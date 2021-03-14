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
    private val mapper: ModelMapper<CharacterVo, CharacterModel>,
    private val networkErrorHandler: NetworkErrorHandler
) : CharacterRepository {

    override suspend fun getData(pageIndex: String): Either<NoDataFound, CharacterModel> {
        return try {
            mapper.transform(api.getData(pageIndex)).right()
        } catch (throwable: Throwable) {
            networkErrorHandler.generateNoDataFoundCustomException(throwable, "Character").left()
        }
    }
}