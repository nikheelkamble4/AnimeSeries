package com.example.animeseries.screens

import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.animeseries.AnimeViewModel
import com.example.animeseries.model.AnimeModel
import com.example.animeseries.util.NetworkResult

@Composable
fun DetailsScreen(navController: NavController, id: Int?, viewModel: AnimeViewModel) {
    val context = LocalContext.current
    LaunchedEffect(key1 = id) {
        if (id != null) {
            viewModel.getAnimeById(id, context)
        }
    }

    val animeResponse by viewModel.animeDetailsLiveData.observeAsState()
    when(animeResponse) {
        is NetworkResult.Success<*> -> {
            val anime = animeResponse?.data
            AnimeDetails(anime, navController)
        }
        is NetworkResult.Loading<*> -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        }
        is NetworkResult.Error<*> -> {
            Toast.makeText(context, animeResponse?.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
        }
        else -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetails(anime: AnimeModel?, navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Row {
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Anime")
            }
        }, colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
            navigationIcon = {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Arrow Back",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
            })
    }) {
        Surface(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.padding(4.dp)) {
                if (anime != null) {
                    Text(text = anime.title, style = MaterialTheme.typography.headlineSmall)
                    Log.d("Nikheel", "DetailsScreeanime.trailern: ${anime.trailer}")
                    if (anime.trailer != null) {
                        AndroidView(
                            factory = {
                                WebView(it).apply {
                                    settings.javaScriptEnabled = true
                                    loadUrl(anime.trailer)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    } else {
                        Image(
                            painter = rememberImagePainter(
                                data = anime.image_trailer,
                                builder = {
                                    crossfade(true)
                                    transformations(CircleCropTransformation())
                                }),
                            contentDescription = "Anime Poster"
                        )
                    }
                    val scrollState = rememberScrollState()
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        Text(
                            text = "Plot/Synopsis:",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = anime.synopsis,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "Genre(s):",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = anime.genres,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "Number of Episodes:",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = anime.episodes.toString(),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "Rating:",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = anime.rating,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}