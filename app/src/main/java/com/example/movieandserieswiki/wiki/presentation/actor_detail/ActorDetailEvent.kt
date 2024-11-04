package com.example.movieandserieswiki.wiki.presentation.actor_detail

import com.example.movieandserieswiki.core.domain.util.NetworkError
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListEvent

interface ActorDetailEvent {
    data class Error(val error: NetworkError): ActorDetailEvent
}