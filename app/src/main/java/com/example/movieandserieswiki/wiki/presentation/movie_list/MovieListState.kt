package com.example.movieandserieswiki.wiki.presentation.movie_list
import com.example.movieandserieswiki.wiki.presentation.models.MovieUi
data class MovieListState(
    val isLoading:Boolean = false,
    val popularMovies:List<MovieUi> = emptyList(),
    val upcomingMovies:List<MovieUi> = emptyList(),
    val nowPlayingMovies:List<MovieUi> = emptyList(),
    val movieDetail:MovieUi? = null,
)
