package com.fukuhara.network

interface RestClient {
    fun <T> getApi(client: Class<T>) : T
}