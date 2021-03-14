package com.fukuhara.rickandmortyfunapp.feature.character.business

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * from character_table WHERE page_index = :pageIndex ORDER BY page_index ASC")
    suspend fun getData(pageIndex: String): CharacterModel?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(characterModel: CharacterModel)

    @Query("DELETE FROM character_table")
    suspend fun deleteAll()
}