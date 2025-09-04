package com.example.animeseries.repository

import com.example.animeseries.api.AnimeAPI
import javax.inject.Inject

class AnimeRepository @Inject constructor(private val api: AnimeAPI) {

    suspend fun getTopAnime() = api.getTopAnime()

    suspend fun getAnimeById(id: Int) = api.getAnimeById(id)
}