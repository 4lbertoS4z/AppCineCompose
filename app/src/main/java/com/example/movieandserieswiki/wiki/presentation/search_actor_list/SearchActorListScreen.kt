package com.example.movieandserieswiki.wiki.presentation.search_actor_list

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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import com.example.compose.primaryContainerLightMediumContrast
import com.example.movieandserieswiki.wiki.presentation.search_actor_list.components.SearchActorListInfoCard

@Composable
fun SearchActorListScreen(
    viewModel: SearchActorListViewModel,
    state: SearchActorListState,
    onAction: (SearchActorListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    // Estado de búsqueda
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // Resultados de búsqueda de actores
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
                // Campo de búsqueda
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
                            label = { Text("Buscar actores") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1
                        )
                    }
                    Button(
                        onClick = {
                            val formattedQuery = searchQuery.text.replace(" ", "-")
                            viewModel.onAction(SearchActorListAction.OnSearchQueryChanged(formattedQuery))
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Buscar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de actores
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2)
                ) {
                    items(searchState.size) { index ->
                        val actor = searchState[index]
                        actor?.let {
                            SearchActorListInfoCard(
                                actorUi = it,
                                modifier = Modifier.padding(8.dp),
                                onClick = { onAction(SearchActorListAction.OnActorSelected(it)) }
                            )
                        }
                    }
                }
            }
        }
    }
}