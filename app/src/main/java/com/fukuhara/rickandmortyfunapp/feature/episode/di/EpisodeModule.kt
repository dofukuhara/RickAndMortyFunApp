package com.fukuhara.rickandmortyfunapp.feature.episode.di

import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.network.RestClient
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeApi
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeModel
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeRepository
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeRepositoryImpl
import com.fukuhara.rickandmortyfunapp.feature.episode.business.vo.EpisodeModelMapper
import com.fukuhara.rickandmortyfunapp.feature.episode.business.vo.EpisodeVo
import com.fukuhara.rickandmortyfunapp.feature.episode.EpisodeViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object EpisodeModule {
    private const val MODEL_MAPPER_EPISODE = "EpisodeModelMapper"

    val instance = module {

        factory { get<RestClient>().getApi(EpisodeApi::class.java) }
        factory<ModelMapper<EpisodeVo, EpisodeModel>>(named(MODEL_MAPPER_EPISODE)) { EpisodeModelMapper() }

        factory<EpisodeRepository> {
            EpisodeRepositoryImpl(
                api = get(),
                dao = get(),
                modelMapper = get(named(MODEL_MAPPER_EPISODE)),
                networkErrorHandler = get()
            )
        }

        viewModel {
            EpisodeViewModel(
                repository = get(),
                backgroundDispatcher = Dispatchers.IO
            )
        }
    }
}