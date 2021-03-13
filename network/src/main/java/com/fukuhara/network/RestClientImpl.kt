package com.fukuhara.network

import com.fukuhara.common.provider.ConfigurationProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class RestClientImpl(private val configurationProvider: ConfigurationProvider) : RestClient {

    override fun <T> getApi(client: Class<T>): T {
        return getClient().create(client)
    }

    private fun getClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(configurationProvider.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .loggingInterceptor()
            .build()

    private fun Retrofit.Builder.loggingInterceptor() : Retrofit.Builder =
        if (BuildConfig.DEBUG.not()) {
            this
        } else {
            val clientConfig = OkHttpClient.Builder().addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            ).build()
            this.client(clientConfig)
        }
}