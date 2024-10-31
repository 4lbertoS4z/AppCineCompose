package com.example.movieandserieswiki.wiki.presentation.tv_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieandserieswiki.wiki.presentation.models.toTvUi
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

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Text("Popular", modifier = Modifier.padding(20.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("En emisión", modifier = Modifier.padding(20.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Mejor valoradas", modifier = Modifier.padding(20.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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