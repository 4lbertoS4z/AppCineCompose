package com.example.movieandserieswiki.wiki.domain

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val posterPath: String?,
    val backdropPath: String?,
    val popularity: Double,
    val genres: List<Genre>? = null,
    val runtime: Int? = null,
    val videos: VideosResponse? = null,  // Nota: VideosResponse
    val credits: CreditsResponse? = null  // Nota: CreditsResponse
)

data class Genre(
    val id: Int,
    val name: String
)

data class VideosResponse(  // Cambio a VideosResponse para que coincida
    val results: List<Video>
)

data class Video(
    val key: String,
    val name: String,
    val site: String,  // Por ejemplo, YouTube
    val type: String   // Ej. "Trailer"
)

data class CreditsResponse(  // Cambio a CreditsResponse para que coincida
    val cast: List<Cast>,
    val crew: List<Crew>
)

data class Cast(
    val castId: Int? = null,  // Opcional
    val character: String,
    val name: String,
    val profilePath: String? = null
)

data class Crew(
    val job: String,
    val name: String,
    val profilePath: String? = null
)