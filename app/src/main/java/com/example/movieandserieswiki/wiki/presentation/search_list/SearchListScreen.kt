package com.example.movieandserieswiki.wiki.presentation.search_list

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListAction
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListState
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListViewModel
import com.example.movieandserieswiki.wiki.presentation.movie_list.components.MovieListItem

@Composable
fun SearchListScreen(
    viewModel: MovieListViewModel,
    state: MovieListState,
    onAction: (MovieListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    // Estado de búsqueda
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // Obtenemos los resultados de búsqueda
    val searchState by viewModel.searchState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                // Campo de búsqueda con desplazamiento horizontal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .horizontalScroll(rememberScrollState())  // Permite el desplazamiento horizontal
                    ) {
                        TextField(
                            value = searchQuery,
                            onValueChange = {
                                searchQuery = it
                            },
                            label = { Text("Buscar películas") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1
                        )
                    }
                    Button(
                        onClick = {
                            val formattedQuery = searchQuery.text.replace(" ", "-")
                            // Al hacer clic en la lupa, hacemos la búsqueda
                            viewModel.onAction(MovieListAction.OnSearchQueryChanged(formattedQuery))
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Buscar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar los resultados de la búsqueda
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(searchState) { movie ->
                        movie?.let {
                            MovieListItem(movieUi = it,
                                modifier = Modifier.padding(8.dp),
                                onClick = { onAction(MovieListAction.OnMovieSelected(it)) })
                        }
                    }
                }
            }
        }
    }
}