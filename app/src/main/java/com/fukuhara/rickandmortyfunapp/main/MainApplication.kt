package com.fukuhara.rickandmortyfunapp.main

import android.app.Application
import com.fukuhara.common.provider.di.CommonModule
import com.fukuhara.network.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@MainApplication)
            loadKoinModules(listOf(
                NetworkModule.instance,
                CommonModule.instance
            ))
        }
    }
}