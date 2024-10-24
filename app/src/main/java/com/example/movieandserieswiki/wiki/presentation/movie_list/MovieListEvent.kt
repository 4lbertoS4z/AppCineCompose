package com.example.movieandserieswiki.wiki.presentation.movie_list

import com.example.movieandserieswiki.core.domain.util.NetworkError

interface MovieListEvent {
    data class Error(val error: NetworkError): MovieListEvent
}