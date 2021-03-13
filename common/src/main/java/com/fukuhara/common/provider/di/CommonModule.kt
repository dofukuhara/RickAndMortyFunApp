package com.fukuhara.common.provider.di

import com.fukuhara.common.provider.ConfigurationProvider
import com.fukuhara.common.provider.ConfigurationProviderImpl
import org.koin.dsl.module

object CommonModule {

    val instance = module {
        factory<ConfigurationProvider> { ConfigurationProviderImpl() }
    }
}