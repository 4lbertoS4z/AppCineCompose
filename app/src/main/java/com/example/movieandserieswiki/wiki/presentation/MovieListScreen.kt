package com.example.movieandserieswiki.wiki.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import com.example.movieandserieswiki.wiki.presentation.components.MovieListItem

@Composable
fun MovieListScreen(
    state: MovieListState,
  //  onAction: (MovieListAction) -> Unit,
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.movies) { movieUi ->
                MovieListItem(
                    movieUi = movieUi, modifier = Modifier.fillMaxSize()
                )
                HorizontalDivider()
            }
        }
    }
}