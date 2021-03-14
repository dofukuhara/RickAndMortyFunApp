package com.fukuhara.rickandmortyfunapp.feature.location.di

import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.network.RestClient
import com.fukuhara.rickandmortyfunapp.feature.location.LocationViewModel
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationApi
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationModel
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationRepository
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationRepositoryImpl
import com.fukuhara.rickandmortyfunapp.feature.location.business.vo.LocationModelMapper
import com.fukuhara.rickandmortyfunapp.feature.location.business.vo.LocationVo
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object LocationModule {
    private const val MODEL_MAPPER_LOCATION = "LocationModelMapper"

    val instance = module {
        factory { get<RestClient>().getApi(LocationApi::class.java) }
        factory<ModelMapper<LocationVo, LocationModel>>(named(MODEL_MAPPER_LOCATION)) { LocationModelMapper() }

        factory<LocationRepository> {
            LocationRepositoryImpl(
                api = get(),
                dao = get(),
                modelMapper = get(named(MODEL_MAPPER_LOCATION)),
                networkErrorHandler = get()
            )
        }

        viewModel {
            LocationViewModel(
                repository = get(),
                backgroundDispatcher = Dispatchers.IO
            )
        }
    }
}