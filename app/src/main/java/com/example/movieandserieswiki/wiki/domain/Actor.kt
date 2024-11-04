package com.example.movieandserieswiki.wiki.domain

data class Actor(
    val id: Int,
    val name: String,
    val biography: String,
    val birthday: String? = null,
    val deathday: String? = null,
    val placeOfBirth: String? = null,
    val profilePath: String? = null,
    val popularity: Double,
    val credits: ActorCreditsResponse? = null
)

data class ActorCreditsResponse(
    val cast: List<ActorCast>,
    val crew: List<ActorCrew>
)

data class ActorCast(
    val title: String,
    val character: String,
    val releaseDate: String? = null,
    val posterPath: String? = null,
    val voteAverage: Double,
    val overview: String,
    val mediaType: String // "movie" or "tv"
)

data class ActorCrew(
    val title: String,
    val job: String,
    val releaseDate: String? = null,
    val posterPath: String? = null,
    val voteAverage: Double,
    val overview: String,
    val mediaType: String // "movie" or "tv"
)