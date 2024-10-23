package com.example.movieandserieswiki.wiki.presentation

import com.example.movieandserieswiki.wiki.presentation.models.MovieUi

interface MovieListAction {
    data class OnMovieSelected(val movie: MovieUi) : MovieListAction
}