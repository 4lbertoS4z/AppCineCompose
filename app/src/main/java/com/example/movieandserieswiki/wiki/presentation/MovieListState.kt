package com.example.movieandserieswiki.wiki.presentation
import com.example.movieandserieswiki.wiki.presentation.models.MovieUi
data class MovieListState(
    val isLoading:Boolean = false,
    val movies:List<MovieUi> = emptyList(),
    val selectedMovie:MovieUi? = null,
)
