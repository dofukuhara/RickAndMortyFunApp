package com.fukuhara.rickandmortyfunapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fukuhara.rickandmortyfunapp.database.converter.CharacterConverter
import com.fukuhara.rickandmortyfunapp.database.converter.EpisodeConverters
import com.fukuhara.rickandmortyfunapp.database.converter.LocationConverter
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterDao
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterModel
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeDao
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeModel
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationDao
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationModel

@Database(entities = [EpisodeModel::class, CharacterModel::class, LocationModel::class], version = 1, exportSchema = false)
@TypeConverters(EpisodeConverters::class, CharacterConverter::class, LocationConverter::class)
abstract class RickAndMortyDb : RoomDatabase() {

    abstract fun episodeDao(): EpisodeDao
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
}