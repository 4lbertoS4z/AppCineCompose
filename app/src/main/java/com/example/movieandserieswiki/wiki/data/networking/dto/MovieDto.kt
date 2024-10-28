package com.example.movieandserieswiki.wiki.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    @SerialName("original_title") val originalTitle: String? = null,  // Cambiado a opcional
    val overview: String,
    @SerialName("vote_average") val voteAverage: Double? = null,  // Cambiado a opcional
    @SerialName("vote_count") val voteCount: Int? = null,  // Cambiado a opcional
    @SerialName("release_date") val releaseDate: String? = null,  // Cambiado a opcional
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    val popularity: Double?=null,
    val genres: List<GenreDto>? = null,
    val runtime: Int? = null,
    val videos: VideosDto? = null,
    val credits: CreditsDto? = null
)
@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)
@Serializable
data class VideosDto(
    val results: List<VideoDto>
)

@Serializable
data class VideoDto(
    val key: String,
    val name: String,
    val site: String,  // Por ejemplo, YouTube
    val type: String   // Ej. "Trailer"
)
@Serializable
data class CreditsDto(
    val cast: List<CastDto>,
    val crew: List<CrewDto>
)

@Serializable
data class CastDto(
    val castId: Int? = null,
    val character: String,
    val name: String,
    val profilePath: String? = null
)

@Serializable
data class CrewDto(
    val job: String,
    val name: String,
    val profilePath: String? = null
)