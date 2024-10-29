package com.example.movieandserieswiki.wiki.presentation.movie_detail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.movieandserieswiki.wiki.presentation.movie_detail.components.InfoCard
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListState

@Composable
fun MovieDetailScreen(state: MovieListState, modifier: Modifier = Modifier) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
    } else if (state.movieDetail != null) {
        val movie = state.movieDetail
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            movie.posterPath?.let {
                item {
                    InfoCard(
                        title = movie.title,
                        releaseDate = movie.releaseDate,
                        voteAverage = movie.voteAverage.value,
                        overview = movie.overview,
                        posterPath = movie.posterPath ?: "",
                        genres = movie.genres,
                        cast = movie.cast,
                        videos = movie.videos
                    )
                }
            }
        }
    }
}