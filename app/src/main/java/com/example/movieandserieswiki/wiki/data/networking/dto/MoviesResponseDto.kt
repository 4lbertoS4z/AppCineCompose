package com.example.movieandserieswiki.wiki.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponseDto(
    val results: List<MovieDto>
)
