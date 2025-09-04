package com.example.animeseries.api

import com.example.animeseries.model.TopAnime
import com.example.animeseries.model.TopAnimes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface AnimeAPI {

    @GET("v4/top/anime")
    suspend fun getTopAnime(): Response<TopAnime>

    @GET("v4/anime/{id}")
    suspend fun getAnimeById(@Path("id") animeId: Int): Response<TopAnimes>
}