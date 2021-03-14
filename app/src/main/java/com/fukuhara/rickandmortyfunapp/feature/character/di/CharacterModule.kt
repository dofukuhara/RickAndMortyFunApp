package com.fukuhara.rickandmortyfunapp.feature.character.di

import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.network.RestClient
import com.fukuhara.rickandmortyfunapp.feature.character.CharacterViewModel
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterApi
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterModel
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterRepository
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterRepositoryImpl
import com.fukuhara.rickandmortyfunapp.feature.character.business.vo.CharacterModelMapper
import com.fukuhara.rickandmortyfunapp.feature.character.business.vo.CharacterVo
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object CharacterModule {
    private const val MODEL_MAPPER_CHARACTER = "CharacterModelMapper"

    val instance = module {
        factory { get<RestClient>().getApi(CharacterApi::class.java) }
        factory<ModelMapper<CharacterVo, CharacterModel>>(named(MODEL_MAPPER_CHARACTER)) { CharacterModelMapper() }

        factory<CharacterRepository> {
            CharacterRepositoryImpl(
                api = get(),
                mapper = get(named(MODEL_MAPPER_CHARACTER)),
                networkErrorHandler = get()
            )
        }

        viewModel {
            CharacterViewModel(
                repository = get(),
                backgroundDispatcher = Dispatchers.IO
            )
        }
    }
}