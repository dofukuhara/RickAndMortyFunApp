package com.fukuhara.rickandmortyfunapp.feature.episode.business

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EpisodeDao {

    @Query("SELECT * from episode_table WHERE page_index = :pageIndex ORDER BY page_index ASC")
    suspend fun getData(pageIndex: String): EpisodeModel?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(episodeModel: EpisodeModel)

    @Query("DELETE FROM episode_table")
    suspend fun deleteAll()
}