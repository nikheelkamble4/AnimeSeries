package com.example.animeseries.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.animeseries.AnimeViewModel
import com.example.animeseries.screens.DetailsScreen
import com.example.animeseries.screens.HomeScreen

@Composable
fun AnimeNavigation() {
    val navController = rememberNavController()
    val viewModel: AnimeViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = AnimeScreens.HomeScreen.name) {
        composable(route = AnimeScreens.HomeScreen.name) {
            HomeScreen(navController, viewModel)
        }

        composable(
            route = AnimeScreens.DetailsScreen.name + "/{anime}",
            arguments = listOf(navArgument(name = "anime") {
                type = NavType.IntType
            })
        ) {
            DetailsScreen(navController, it.arguments?.getInt("anime"), viewModel)
        }
    }
}