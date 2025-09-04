package com.example.animeseries.model

data class Data(
    val episodes: Int,
    val genres: List<Genre>,
    val images: Images,
    val mal_id: Int,
    val rating: String,
    val synopsis: String,
    val title: String,
    val trailer: Trailer
)