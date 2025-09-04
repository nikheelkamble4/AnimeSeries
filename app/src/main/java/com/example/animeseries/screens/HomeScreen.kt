package com.example.animeseries.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.animeseries.AnimeViewModel
import com.example.animeseries.navigation.AnimeScreens
import com.example.animeseries.widget.AnimeRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: AnimeViewModel) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Anime")
        })
    }) {
        MainContent(it, navController, viewModel)
    }
}

@Composable
fun MainContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: AnimeViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.getTopAnimes()
    }
    val animeResponse by viewModel.animeResponseLiveData.observeAsState()

    val list = animeResponse?.data?.data ?: emptyList()

    Surface(modifier = Modifier.padding(paddingValues),
        color = MaterialTheme.colorScheme.background) {
        LazyColumn(modifier = Modifier.padding(12.dp)) {
            items(list) { item ->
                AnimeRow(item) { anime ->
                    navController.navigate(route = AnimeScreens.DetailsScreen.name + "/$anime")
                }
            }
        }
    }
}