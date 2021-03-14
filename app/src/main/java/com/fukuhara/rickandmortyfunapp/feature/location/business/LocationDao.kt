package com.fukuhara.rickandmortyfunapp.feature.location.business

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {

    @Query("SELECT * from location_table WHERE page_index = :pageIndex ORDER BY page_index ASC")
    suspend fun getData(pageIndex: String): LocationModel?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(locationModel: LocationModel)

    @Query("DELETE FROM location_table")
    suspend fun deleteAll()
}