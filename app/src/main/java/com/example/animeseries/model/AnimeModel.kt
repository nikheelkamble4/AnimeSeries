package com.example.animeseries.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimeModel(
    @PrimaryKey
    val mal_id: Int,
    val episodes: Int,
    val genres: String,
    val images: String,
    val rating: String,
    val synopsis: String,
    val title: String,
    val image_trailer: String?,
    val trailer: String?
)