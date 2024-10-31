package com.example.movieandserieswiki.wiki.presentation.tv_list

import com.example.movieandserieswiki.core.domain.util.NetworkError

interface TvListEvent {
    data class Error(val error: NetworkError): TvListEvent
}