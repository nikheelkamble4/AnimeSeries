package com.example.animeseries.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animeseries.model.AnimeModel

@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAnime(animeModel: List<AnimeModel>)

    @Query("SELECT * FROM AnimeModel ORDER BY title ASC")
    suspend fun getAnime(): List<AnimeModel>

    @Query("SELECT * FROM AnimeModel WHERE mal_id =:id")
    suspend fun getAnimeById(id: Int): AnimeModel
}