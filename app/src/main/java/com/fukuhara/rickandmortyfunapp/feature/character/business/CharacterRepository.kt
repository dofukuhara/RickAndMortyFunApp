package com.fukuhara.rickandmortyfunapp.feature.character.business

import com.fukuhara.common.arch.Either
import com.fukuhara.common.exception.NoDataFound

interface CharacterRepository {
    suspend fun getData(pageIndex: String) : Either<NoDataFound, CharacterModel>
}