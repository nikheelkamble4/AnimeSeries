package com.example.animeseries.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.animeseries.api.AnimeAPI
import com.example.animeseries.model.TopAnime
import com.example.animeseries.util.NetworkResult
import javax.inject.Inject

class AnimeRepository @Inject constructor(private val api: AnimeAPI) {

    suspend fun getTopAnime() = api.getTopAnime()

    suspend fun getAnimeById(id: Int) = api.getAnimeById(id)
}