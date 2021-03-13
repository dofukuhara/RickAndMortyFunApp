package com.fukuhara.common.provider

internal class ConfigurationProviderImpl: ConfigurationProvider {
    override fun getBaseUrl(): String = "https://rickandmortyapi.com/api/"
}