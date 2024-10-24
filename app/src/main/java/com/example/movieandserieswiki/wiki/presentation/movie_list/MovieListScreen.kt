package com.example.movieandserieswiki.wiki.presentation.movie_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import com.example.movieandserieswiki.wiki.presentation.movie_list.components.MovieListItem

@Composable
fun MovieListScreen(
    state: MovieListState,
    onAction: (MovieListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Sección para las películas en cartelera
            item {
                Text("En cartelera", modifier = Modifier.padding(20.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.nowPlayingMovies) { movieUi ->
                        MovieListItem(
                            movieUi = movieUi,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .fillMaxHeight(),
                            onClick = {
                                onAction(MovieListAction.OnMovieSelected(movieUi))
                            }
                        )
                    }
                }
            }

            // Espacio entre las secciones
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Sección para las próximas películas (Upcoming)
            item {
                Text("Próximamente", modifier = Modifier.padding(20.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.upcomingMovies) { movieUi ->
                        MovieListItem(
                            movieUi = movieUi,
                            modifier = Modifier.fillMaxWidth(0.60f),
                            onClick = {
                                onAction(MovieListAction.OnMovieSelected(movieUi))
                            }
                        )
                    }
                }
            }

            // Espacio entre las secciones
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Sección para las películas populares
            item {
                Text("Populares", modifier = Modifier.padding(20.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.popularMovies) { movieUi ->
                        MovieListItem(
                            movieUi = movieUi,
                            modifier = Modifier.fillMaxWidth(0.60f),
                            onClick = {
                                onAction(MovieListAction.OnMovieSelected(movieUi))
                            }
                        )
                    }
                }
            }
        }
    }
}