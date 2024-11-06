package com.example.movieandserieswiki.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieandserieswiki.wiki.presentation.actor_detail.ActorDetailScreen

@Composable
fun MainNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "movies",
        modifier = modifier
    ) {
        composable("movies") {
            AdaptativeMovieListDetailPane(navController = navController)
        }
        composable("series") {
            AdaptativeSeriesListDetailPane(navController = navController)
        }
        composable("actor/{actorId}") { backStackEntry ->
            val actorId = backStackEntry.arguments?.getString("actorId")?.toInt() ?: 0
            ActorDetailScreen(actorId = actorId)
        }
    }
}