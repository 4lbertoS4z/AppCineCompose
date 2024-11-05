package com.example.movieandserieswiki.wiki.presentation.models

import com.example.movieandserieswiki.wiki.domain.Tv
import java.text.NumberFormat
import java.util.Locale

data class TvUi(
    val id: Int,
    val name: String,
    val originalName: String,
    val overview: String,
    val voteAverage: TvDisplayableNumber,
    val voteCount: Int,
    val firstAirDate: String?,
    val lastAirDate: String?,
    val numberOfEpisodes: Int?,
    val numberOfSeasons: Int?,
    val posterPath: String?,
    val backdropPath: String?,
    val popularity: TvDisplayableNumber,
    val cast: List<TvCastUi> = emptyList(),
    val genres: List<TvGenreUi> = emptyList(),
    val videos: List<TvVideoUi> = emptyList()
)

data class TvGenreUi(
    val id: Int,
    val name: String
)

data class TvCastUi(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String? = null,
    val popularity: Double? = null
)

data class TvVideoUi(
    val key: String,
    val name: String
)

data class TvDisplayableNumber(
    val value: Double,
    val formatted: String
)

fun Tv.toTvUi(): TvUi {
    return TvUi(
        id = id,
        name = name,
        originalName = originalName,
        overview = overview,
        voteAverage = voteAverage.toTvDisplayableNumber(),
        voteCount = voteCount,
        firstAirDate = firstAirDate,
        lastAirDate = lastAirDate,
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        posterPath = posterPath,
        backdropPath = backdropPath,
        popularity = popularity.toTvDisplayableNumber(),
        cast = credits?.cast?.map { TvCastUi(id = it.castId ?: -1, it.name, it.character, it.profilePath, it.popularity) }
            ?: emptyList(),
        genres = genres?.map { TvGenreUi(it.id, it.name) }
            ?: emptyList(),
        videos = videos?.results?.map { TvVideoUi(it.key, it.name) }
            ?: emptyList()
    )
}

fun Double.toTvDisplayableNumber(): TvDisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return TvDisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}