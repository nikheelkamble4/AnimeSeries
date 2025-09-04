package com.example.animeseries.api

import com.example.animeseries.model.TopAnime
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface AnimeAPI {

    @GET("v4/top/anime")
    suspend fun getTopAnime(): Response<TopAnime>
}