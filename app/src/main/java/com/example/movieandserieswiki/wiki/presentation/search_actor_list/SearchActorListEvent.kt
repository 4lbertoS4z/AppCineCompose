package com.example.movieandserieswiki.wiki.presentation.search_actor_list

import com.example.movieandserieswiki.core.domain.util.NetworkError

interface SearchActorListEvent {
    data class Error(val error: NetworkError): SearchActorListEvent

}