package com.example.movieandserieswiki.wiki.presentation

import com.example.movieandserieswiki.core.domain.util.NetworkError

interface MovieListEvent {
    data class Error(val error: NetworkError):MovieListEvent
}