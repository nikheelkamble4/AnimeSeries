package com.example.animeseries.repository

import com.example.animeseries.api.AnimeAPI
import com.example.animeseries.db.AnimeDB
import com.example.animeseries.model.AnimeModel
import javax.inject.Inject

class AnimeRepository @Inject constructor(private val api: AnimeAPI, private val animeDB: AnimeDB) {

    suspend fun getTopAnime() = api.getTopAnime()

    suspend fun getAnimeById(id: Int) = api.getAnimeById(id)

    suspend fun addAnime(animeModel: List<AnimeModel>) = animeDB.getAnimeDao().addAnime(animeModel)

    suspend fun getAnimeOffline() = animeDB.getAnimeDao().getAnime()

    suspend fun getAnimeByIdOffline(id: Int) = animeDB.getAnimeDao().getAnimeById(id)
}