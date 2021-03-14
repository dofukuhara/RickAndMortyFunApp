package com.fukuhara.network.di

import com.fukuhara.network.NetworkErrorHandler
import com.fukuhara.network.NetworkErrorHandlerImpl
import com.fukuhara.network.RestClient
import com.fukuhara.network.RestClientImpl
import org.koin.dsl.module

object NetworkModule {

    val instance = module {
        single<RestClient> { RestClientImpl(configurationProvider = get()) }

        factory<NetworkErrorHandler> { NetworkErrorHandlerImpl() }
    }
}