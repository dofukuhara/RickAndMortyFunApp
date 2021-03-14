package com.fukuhara.common.provider

import com.fukuhara.common.BuildConfig


internal class ConfigurationProviderImpl: ConfigurationProvider {
    override fun getBaseUrl(): String = "https://rickandmortyapi.com/api/"
    override fun isDebugBuild(): Boolean = BuildConfig.DEBUG
}