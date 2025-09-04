package com.example.animeseries.model

data class TopAnime(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta,
    val pagination: Pagination
)