package com.example.movieandserieswiki.wiki.presentation.movie_detail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.movieandserieswiki.wiki.presentation.movie_detail.components.InfoCard
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListState

@Composable
fun MovieDetailScreen(state: MovieListState, modifier: Modifier = Modifier) {
    val contentColor = if(isSystemInDarkTheme()){
        Color.White
    }else{
        Color.Black
    }
    if(state.isLoading){
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    }else if(state.movieDetail != null){
        val movie = state.movieDetail
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InfoCard(movie.title, movie.releaseDate, movie.voteAverage.toString(), movie.overview)
        }
    }
}