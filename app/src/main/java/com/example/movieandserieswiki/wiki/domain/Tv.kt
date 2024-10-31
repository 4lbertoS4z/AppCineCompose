package com.example.movieandserieswiki.wiki.domain


data class Tv(
    val id: Int,
    val name: String,
    val originalName: String,
    val overview: String,
    val voteAverage: Double,
    val voteCount: Int,
    val firstAirDate: String,
    val lastAirDate: String? = null,
    val numberOfEpisodes: Int? = null,
    val numberOfSeasons: Int? = null,
    val posterPath: String?,
    val backdropPath: String?,
    val popularity: Double,
    val genres: List<TvGenre>? = null,
    val videos: TvVideosResponse? = null,
    val credits: TvCreditsResponse? = null
)

data class TvGenre(
    val id: Int,
    val name: String
)

data class TvVideosResponse(
    val results: List<TvVideo>
)

data class TvVideo(
    val key: String,
    val name: String,
    val site: String,  // Ej. "YouTube"
    val type: String   // Ej. "Trailer"
)

data class TvCreditsResponse(
    val cast: List<TvCast>,
    val crew: List<TvCrew>
)

data class TvCast(
    val castId: Int? = null,
    val character: String,
    val name: String,
    val profilePath: String? = null,
    val popularity: Double? = null
)

data class TvCrew(
    val job: String,
    val name: String,
    val profilePath: String? = null
)