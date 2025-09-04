package com.example.animeseries.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.animeseries.model.AnimeModel

@Database(entities = [AnimeModel::class], version = 1)
abstract class AnimeDB: RoomDatabase() {

    abstract fun getAnimeDao(): AnimeDao
}