package com.example.movieandserieswiki.wiki.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class TvResponseDto(
    val results: List<TvDto>

)
