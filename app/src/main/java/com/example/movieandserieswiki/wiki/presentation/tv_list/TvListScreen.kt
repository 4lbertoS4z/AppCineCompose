package com.example.movieandserieswiki.wiki.presentation.tv_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieandserieswiki.wiki.presentation.models.toMovieUi
import com.example.movieandserieswiki.wiki.presentation.models.toTvUi
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListAction
import com.example.movieandserieswiki.wiki.presentation.movie_list.components.MovieListItem
import com.example.movieandserieswiki.wiki.presentation.tv_list.components.TvListItem

@Composable
fun TvListScreen(
    viewModel: TvListViewModel,
    state: TvListState,
    onAction: (TvListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val popularTvs = viewModel.popularTvs.collectAsLazyPagingItems()
    val onTheAirTvs = viewModel.onAirTvs.collectAsLazyPagingItems()
    val topRatedTvs = viewModel.topRatedTvs.collectAsLazyPagingItems()
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("En emisión") }
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = modifier
            .fillMaxSize()
            .padding(16.dp)) {
            // Dropdown menu
            Box {
                Button(onClick = { expanded = !expanded }) {
                    Text(text = selectedCategory)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("En emisión") },
                        onClick = {
                            selectedCategory = "En emisión"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Mejor valoradas") },
                        onClick = {
                            selectedCategory = "Mejor valoradas"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Popular") },
                        onClick = {
                            selectedCategory = "Popular"
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            when (selectedCategory) {
                "En emisión" -> {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2)
                    ) {
                        items(onTheAirTvs.itemCount) { index ->
                            val tv = onTheAirTvs[index]
                            tv?.let {
                                TvListItem(
                                    tvUi = it.toTvUi(),
                                    modifier = Modifier
                                        .fillMaxWidth(0.1f)
                                        .fillMaxHeight(),
                                    onClick = {
                                        onAction(TvListAction.OnTvSelected(it.toTvUi()))
                                    }
                                )
                            }
                        }
                    }
                }

                "Popular" -> {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2)
                    ) {
                        items(popularTvs.itemCount) { index ->
                            val tv = popularTvs[index]
                            tv?.let {
                                TvListItem(
                                    tvUi = it.toTvUi(),
                                    modifier = Modifier
                                        .fillMaxWidth(0.1f)
                                        .fillMaxHeight(),
                                    onClick = {
                                        onAction(TvListAction.OnTvSelected(it.toTvUi()))
                                    }
                                )
                            }
                        }
                    }
                }

                "Mejor valoradas" -> {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2)
                    ) {
                        items(topRatedTvs.itemCount) { index ->
                            val tv = topRatedTvs[index]
                            tv?.let {
                                TvListItem(
                                    tvUi = it.toTvUi(),
                                    modifier = Modifier
                                        .fillMaxWidth(0.1f)
                                        .fillMaxHeight(),
                                    onClick = {
                                        onAction(TvListAction.OnTvSelected(it.toTvUi()))
                                    }
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}