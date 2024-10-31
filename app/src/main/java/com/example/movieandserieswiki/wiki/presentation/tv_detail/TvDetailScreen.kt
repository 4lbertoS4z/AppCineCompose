package com.example.movieandserieswiki.wiki.presentation.tv_detail

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.movieandserieswiki.wiki.presentation.components.YouTubePlayer
import com.example.movieandserieswiki.wiki.presentation.tv_detail.components.TvInfoCard
import com.example.movieandserieswiki.wiki.presentation.tv_list.TvListState

@Composable
fun TvDetailScreen(state: TvListState, modifier: Modifier = Modifier) {
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
    } else if (state.tvDetail != null) {
        val tv = state.tvDetail
        Log.d("MovieDetailScreen", "Videos encontrados: ${tv.videos.size}")

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            tv.posterPath?.let {
                item {
                    TvInfoCard(
                        name = tv.name,
                        firstAirDate = tv.firstAirDate ?: "",
                        voteAverage = tv.voteAverage.value,
                        overview = tv.overview,
                        posterPath = tv.posterPath ?: "",
                        genres = tv.genres,
                        cast = tv.cast,
                        modifier = Modifier.padding(bottom = 16.dp) // Añadir un margen inferior
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                }
            }
            items(tv.videos){video ->
                YouTubePlayer(
                    videoKey = video.key,
                    lifecycleOwner = LocalLifecycleOwner.current,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp))
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}