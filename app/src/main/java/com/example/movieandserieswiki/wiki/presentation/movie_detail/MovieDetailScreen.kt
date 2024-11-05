package com.example.movieandserieswiki.wiki.presentation.movie_detail

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.movieandserieswiki.wiki.presentation.actor_detail.ActorDetailAction
import com.example.movieandserieswiki.wiki.presentation.movie_detail.components.MovieInfoCard
import com.example.movieandserieswiki.wiki.presentation.components.YouTubePlayer
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListState

@Composable
fun MovieDetailScreen(state: MovieListState,actorClicked: (Int) -> Unit, modifier: Modifier = Modifier) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.movieDetail != null) {
        val movie = state.movieDetail
        Log.d("MovieDetailScreen", "Videos encontrados: ${movie.videos.size}")

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            movie.posterPath?.let {
                item {
                    MovieInfoCard(
                        title = movie.title,
                        releaseDate = movie.releaseDate,
                        voteAverage = movie.voteAverage.value,
                        overview = movie.overview,
                        posterPath = movie.posterPath ?: "",
                        genres = movie.genres,
                        cast = movie.cast,
                        actorClicked = { actorId ->
                            actorClicked(actorId) // Pasar solo el actorId
                        },
                        modifier = Modifier.padding(bottom = 16.dp) // Añadir un margen inferior
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }


            // Cargar todos los videos disponibles
            if (movie.videos.isNotEmpty()) {
                item {
                    Text(
                        text = "Videos:",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                    )
                }

                items(movie.videos) { video ->
                    YouTubePlayer(
                        videoKey = video.key,
                        lifecycleOwner = LocalLifecycleOwner.current,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                }
            }
        }
    }
}