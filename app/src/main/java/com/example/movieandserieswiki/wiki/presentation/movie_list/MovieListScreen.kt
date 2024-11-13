package com.example.movieandserieswiki.wiki.presentation.movie_list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.compose.primaryContainerLightMediumContrast
import com.example.movieandserieswiki.wiki.presentation.models.toMovieUi
import com.example.movieandserieswiki.wiki.presentation.movie_list.components.MovieListItem


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

    var expandedTv by rememberSaveable { mutableStateOf(false) }
    var selectedCategoryTv by rememberSaveable { mutableStateOf("En emisión") }

    // Estado de búsqueda
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // Obtenemos los resultados de búsqueda
    val searchState by viewModel.searchState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryContainerLightMediumContrast)
    ) {


        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Dropdown menu
                Box {
                    Button(onClick = { expandedTv = !expandedTv }) {
                        Text(text = selectedCategoryTv)
                    }
                    DropdownMenu(
                        expanded = expandedTv,
                        onDismissRequest = { expandedTv = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("En emisión") },
                            onClick = {
                                selectedCategoryTv = "En emisión"
                                expandedTv = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Próximamente") },
                            onClick = {
                                selectedCategoryTv = "Próximamente"
                                expandedTv = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Populares") },
                            onClick = {
                                selectedCategoryTv = "Populares"
                                expandedTv = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Buscar") },
                            onClick = {
                                selectedCategoryTv = "Buscar"
                                expandedTv = false
                            }
                        )
                    }
                }
                // Mostrar campo de búsqueda solo si está seleccionada la categoría "Buscar"
                if (selectedCategoryTv == "Buscar") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .horizontalScroll(rememberScrollState())
                        ) {
                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                label = { Text("Buscar películas") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1
                            )
                        }
                        Button(
                            onClick = {
                                val formattedQuery = searchQuery.text.replace(" ", "-")
                                viewModel.onAction(MovieListAction.OnSearchQueryChanged(formattedQuery))
                            },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(Icons.Filled.Search, contentDescription = "Buscar")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar la lista basada en la selección
                when (selectedCategoryTv) {
                    "En emisión" -> {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2)
                        ) {
                            items(nowPlayingMovies.itemCount) { index ->
                                val movie = nowPlayingMovies[index]
                                movie?.let {
                                    MovieListItem(
                                        movieUi = it.toMovieUi(),
                                        modifier = Modifier.padding(8.dp),
                                        onClick = { onAction(MovieListAction.OnMovieSelected(it.toMovieUi())) }
                                    )
                                }
                            }
                        }
                    }

                    "Próximamente" -> {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2)
                        ) {
                            items(upcomingMovies.itemCount) { index ->
                                val movie = upcomingMovies[index]
                                movie?.let {
                                    MovieListItem(
                                        movieUi = it.toMovieUi(),
                                        modifier = Modifier.padding(8.dp),
                                        onClick = { onAction(MovieListAction.OnMovieSelected(it.toMovieUi())) }
                                    )
                                }
                            }
                        }
                    }

                    "Populares" -> {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2)
                        ) {
                            items(popularMovies.itemCount) { index ->
                                val movie = popularMovies[index]
                                movie?.let {
                                    MovieListItem(
                                        movieUi = it.toMovieUi(),
                                        modifier = Modifier.padding(8.dp),
                                        onClick = { onAction(MovieListAction.OnMovieSelected(it.toMovieUi())) }
                                    )
                                }
                            }
                        }
                    }
                    "Buscar" -> {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2)
                        ) {
                            items(searchState.size){ index ->
                                val movie = searchState[index]
                                movie?.let {
                                    MovieListItem(
                                        movieUi = it,
                                        modifier = Modifier.padding(8.dp),
                                        onClick = { onAction(MovieListAction.OnMovieSelected(it)) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}