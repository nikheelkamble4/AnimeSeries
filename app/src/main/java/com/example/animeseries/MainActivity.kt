package com.example.animeseries

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.animeseries.ui.theme.AnimeSeriesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeSeriesTheme {
                Anime()
            }
        }
    }
}

@Composable
fun Anime() {
    val viewModel: AnimeViewModel = hiltViewModel()
    viewModel.animeResponseLiveData.observe(LocalLifecycleOwner.current, Observer {
        Log.d("Nikheel", it.data.toString())
    })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimeSeriesTheme {

    }
}