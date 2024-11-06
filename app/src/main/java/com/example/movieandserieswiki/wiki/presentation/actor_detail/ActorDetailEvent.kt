package com.example.movieandserieswiki.wiki.presentation.actor_detail

import com.example.movieandserieswiki.core.domain.util.NetworkError

interface ActorDetailEvent {
    data class Error(val error: NetworkError): ActorDetailEvent
}