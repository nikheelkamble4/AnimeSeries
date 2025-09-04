package com.example.animeseries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.animeseries.navigation.AnimeNavigation
import com.example.animeseries.screens.HomeScreen
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

@Composable
fun Anime() {
    val viewModel: AnimeViewModel = hiltViewModel()
    val animeResponse by viewModel.animeResponseLiveData.observeAsState()

    val list = animeResponse?.data?.data ?: emptyList()
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimeSeriesTheme {

    }
}