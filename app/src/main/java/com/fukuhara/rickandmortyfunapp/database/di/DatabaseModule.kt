package com.fukuhara.rickandmortyfunapp.database.di

import androidx.room.Room
import com.fukuhara.rickandmortyfunapp.database.RickAndMortyDb
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object DatabaseModule {

    val instance = module {
        single {
            Room.databaseBuilder(
                androidApplication(),
                RickAndMortyDb::class.java,
                "rick-morty-db"
            ).build()
        }

        factory { get<RickAndMortyDb>().episodeDao() }
        factory { get<RickAndMortyDb>().characterDao() }
        factory { get<RickAndMortyDb>().locationDao() }
    }
}