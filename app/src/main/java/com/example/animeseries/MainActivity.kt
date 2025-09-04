package com.example.animeseries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.animeseries.navigation.AnimeNavigation
import com.example.animeseries.ui.theme.AnimeSeriesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeSeriesTheme(darkTheme = false) {
                AnimeNavigation()
            }
        }
    }
}