package com.example.movieandserieswiki.wiki.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvDto(
    val id: Int,
    val name: String,
    @SerialName("original_name") val originalName: String? = null,
    val overview: String,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null,
    @SerialName("last_air_date") val lastAirDate: String? = null,
    @SerialName("number_of_episodes") val numberOfEpisodes: Int? = null,
    @SerialName("number_of_seasons") val numberOfSeasons: Int? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    val popularity: Double? = null,
    val genres: List<TvGenreDto>? = null,
    val videos: TvVideosDto? = null,
    val credits: TvCreditsDto? = null
)

@Serializable
data class TvGenreDto(
    val id: Int,
    val name: String
)

@Serializable
data class TvVideosDto(
    val results: List<TvVideoDto>
)

@Serializable
data class TvVideoDto(
    val key: String,
    val name: String,
    val site: String,  // Ej. "YouTube"
    val type: String   // Ej. "Trailer"
)

@Serializable
data class TvCreditsDto(
    val cast: List<TvCastDto>,
    val crew: List<TvCrewDto>
)

@Serializable
data class TvCastDto(
    @SerialName("cast_id") val castId: Int? = null,
    val character: String,
    val name: String,
    @SerialName("profile_path") val profilePath: String? = null,
    val popularity: Double? = null
)

@Serializable
data class TvCrewDto(
    val job: String,
    val name: String,
    @SerialName("profile_path") val profilePath: String? = null
)