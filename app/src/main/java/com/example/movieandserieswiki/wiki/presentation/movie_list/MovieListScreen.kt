package com.example.movieandserieswiki.wiki.presentation.movie_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieandserieswiki.wiki.presentation.movie_list.components.MovieListItem
import com.example.movieandserieswiki.wiki.presentation.models.toMovieUi
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListAction
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListViewModel
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListState


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel,
    state: MovieListState,
    onAction: (MovieListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val nowPlayingMovies = viewModel.nowPlayingMovies.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.upcomingMovies.collectAsLazyPagingItems()
    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()

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
                    items(nowPlayingMovies.itemCount) { index ->
                        val movie = nowPlayingMovies[index]
                        movie?.let {
                            MovieListItem(
                                movieUi = it.toMovieUi(),
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .fillMaxHeight(),
                                onClick = {
                                    onAction(MovieListAction.OnMovieSelected(it.toMovieUi()))
                                }
                            )
                        }
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
                    items(upcomingMovies.itemCount) { index ->
                        val movie = upcomingMovies[index]
                        movie?.let {
                            MovieListItem(
                                movieUi = it.toMovieUi(),
                                modifier = Modifier.fillMaxWidth(0.60f),
                                onClick = {
                                    onAction(MovieListAction.OnMovieSelected(it.toMovieUi()))
                                }
                            )
                        }
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
                    items(popularMovies.itemCount) { index ->
                        val movie = popularMovies[index]
                        movie?.let {
                            MovieListItem(
                                movieUi = it.toMovieUi(),
                                modifier = Modifier.fillMaxWidth(0.60f),
                                onClick = {
                                    onAction(MovieListAction.OnMovieSelected(it.toMovieUi()))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}