package com.fukuhara.rickandmortyfunapp.main

import android.app.Application
import com.fukuhara.common.provider.di.CommonModule
import com.fukuhara.network.di.NetworkModule
import com.fukuhara.rickandmortyfunapp.database.di.DatabaseModule
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterDao
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeDao
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationDao
import kotlinx.coroutines.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.get

class MainApplication: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@MainApplication)
            loadKoinModules(listOf(
                NetworkModule.instance,
                CommonModule.instance,
                DatabaseModule.instance
            ))
        }

        clearAllDbDataCache()
    }

    private fun clearAllDbDataCache() {
        val episodeDao: EpisodeDao = get()
        val characterDao: CharacterDao = get()
        val locationDao: LocationDao = get()

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            episodeDao.deleteAll()
            characterDao.deleteAll()
            locationDao.deleteAll()
        }
    }
}