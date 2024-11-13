package com.example.movieandserieswiki.wiki.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDto(
    val id: Int,
    val name: String,
    val biography: String? = null,
    @SerialName("birthday") val birthday: String? = null,
    @SerialName("deathday") val deathday: String? = null,
    @SerialName("place_of_birth") val placeOfBirth: String? = null,
    @SerialName("profile_path") val profilePath: String? = null,
    val popularity: Double? = null,
    @SerialName("combined_credits") val credits: ActorCreditsDto? = null
)

@Serializable
data class ActorCreditsDto(
    val cast: List<ActorCastDto>,
    val crew: List<ActorCrewDto>
)

@Serializable
data class ActorCastDto(
    @SerialName("cast_id") val castId: Int? = null,
    val character: String,
    @SerialName("title") val title: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    val overview: String,
    @SerialName("media_type") val mediaType: String // "movie" o "tv"
)

@Serializable
data class ActorCrewDto(
    val job: String,
    @SerialName("title") val title: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    val overview: String,
    @SerialName("media_type") val mediaType: String // "movie" o "tv"
)