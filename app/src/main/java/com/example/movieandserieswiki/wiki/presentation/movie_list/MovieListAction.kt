package com.example.movieandserieswiki.wiki.presentation.movie_list

import com.example.movieandserieswiki.wiki.presentation.models.MovieUi

sealed interface MovieListAction {
    data class OnMovieSelected(val movieUi: MovieUi) : MovieListAction

}