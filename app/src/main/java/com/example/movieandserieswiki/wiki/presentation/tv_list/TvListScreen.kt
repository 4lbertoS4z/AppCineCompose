package com.example.movieandserieswiki.wiki.presentation.tv_list

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
import com.example.movieandserieswiki.wiki.presentation.models.toTvUi
import com.example.movieandserieswiki.wiki.presentation.tv_list.components.TvListInfoCard

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
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedCategory by rememberSaveable { mutableStateOf("En emisión") }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val searchState by viewModel.searchState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryContainerLightMediumContrast)
    ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth(),
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
                        DropdownMenuItem(
                            text = { Text("Buscar") },
                            onClick = {
                                selectedCategory = "Buscar"
                                expanded = false
                            }
                        )
                    }
                }
// Mostrar campo de búsqueda solo si está seleccionada la categoría "Buscar"
                if (selectedCategory == "Buscar") {
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
                                label = { Text("Buscar Series") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1
                            )
                        }
                        Button(
                            onClick = {
                                val formattedQuery = searchQuery.text.replace(" ", "-")
                                viewModel.onAction(TvListAction.OnSearchQueryChanged(formattedQuery))
                            },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(Icons.Filled.Search, contentDescription = "Buscar")
                        }
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
                                    TvListInfoCard(
                                        tvUi = it.toTvUi(),
                                        modifier = Modifier.padding(8.dp),
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
                                    TvListInfoCard(
                                        tvUi = it.toTvUi(),
                                        modifier = Modifier.padding(8.dp),
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
                                    TvListInfoCard(
                                        tvUi = it.toTvUi(),
                                        modifier = Modifier.padding(8.dp),
                                        onClick = {
                                            onAction(TvListAction.OnTvSelected(it.toTvUi()))
                                        }
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
                            items(searchState.size) { index ->
                                val tv = searchState[index]
                                tv?.let {
                                    TvListInfoCard(
                                        tvUi = it,
                                        modifier = Modifier.padding(8.dp),
                                        onClick = { onAction(TvListAction.OnTvSelected(it)) }
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