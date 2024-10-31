package com.example.movieandserieswiki.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "movies",
        modifier = modifier
    ) {
        composable("movies") {
            AdaptativeMovieListDetailPane()
        }
        composable("series") {
            AdaptativeSeriesListDetailPane() // Crear esta función de forma similar a AdaptativeMovieListDetailPane
        }
    }
}