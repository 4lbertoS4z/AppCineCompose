package com.example.movieandserieswiki.wiki.presentation.models

import com.example.movieandserieswiki.wiki.domain.Movie
import java.text.NumberFormat
import java.util.Locale

data class MovieUi(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val voteAverage: DisplayableNumber,
    val voteCount: Int,
    val releaseDate: String,
    val posterPath: String?,
    val backdropPath: String?,
    val popularity: DisplayableNumber,
    val genres: List<GenreUi> = emptyList(),  // Puedes agregar un modelo GenreUi si lo deseas
    val videos: List<VideoUi> = emptyList(),  // Igualmente, si deseas mostrar información de videos
)

data class GenreUi(
    val id: Int,
    val name: String
)

data class VideoUi(
    val key: String,
    val name: String
)
data class DisplayableNumber(
    val value: Double,
    val formatted: String
)

fun Movie.toMovieUi(): MovieUi {
    return MovieUi(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        voteAverage = voteAverage.toDisplayableNumber(),
        voteCount = voteCount,
        releaseDate = releaseDate,
        posterPath = posterPath,
        backdropPath = backdropPath,
        popularity = popularity.toDisplayableNumber(),
        genres = genres?.map { GenreUi(it.id, it.name) } ?: emptyList(),  // Conversión de géneros si existen
        videos = videos?.results?.map { VideoUi(it.key, it.name) } ?: emptyList() // Conversión de videos si existen
    )
}


fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}