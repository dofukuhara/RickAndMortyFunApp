package com.fukuhara.common.provider

interface ConfigurationProvider {
    fun getBaseUrl(): String
    fun isDebugBuild(): Boolean
}