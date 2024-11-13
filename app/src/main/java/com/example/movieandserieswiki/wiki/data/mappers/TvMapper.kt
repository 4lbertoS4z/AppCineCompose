package com.example.movieandserieswiki.wiki.data.mappers

import android.util.Log
import com.example.movieandserieswiki.wiki.data.networking.dto.TvCastDto
import com.example.movieandserieswiki.wiki.data.networking.dto.TvCreditsDto
import com.example.movieandserieswiki.wiki.data.networking.dto.TvCrewDto
import com.example.movieandserieswiki.wiki.data.networking.dto.TvDto
import com.example.movieandserieswiki.wiki.data.networking.dto.TvGenreDto
import com.example.movieandserieswiki.wiki.data.networking.dto.TvVideoDto
import com.example.movieandserieswiki.wiki.data.networking.dto.TvVideosDto
import com.example.movieandserieswiki.wiki.domain.Tv
import com.example.movieandserieswiki.wiki.domain.TvCast
import com.example.movieandserieswiki.wiki.domain.TvCreditsResponse
import com.example.movieandserieswiki.wiki.domain.TvCrew
import com.example.movieandserieswiki.wiki.domain.TvGenre
import com.example.movieandserieswiki.wiki.domain.TvVideo
import com.example.movieandserieswiki.wiki.domain.TvVideosResponse

fun TvDto.toTv(): Tv {
    return Tv(
        id = this.id,
        name = this.name,
        originalName = this.originalName
            ?: "Nombre original no disponible", // Manejo de campo opcional
        overview = this.overview,
        voteAverage = this.voteAverage ?: 0.0, // Manejo de campo opcional
        voteCount = this.voteCount ?: 0, // Manejo de campo opcional
        firstAirDate = this.firstAirDate
            ?: "Fecha de estreno no disponible", // Manejo de campo opcional
        lastAirDate = this.lastAirDate
            ?: "Fecha de última emisión no disponible", // Manejo de campo opcional
        numberOfEpisodes = this.numberOfEpisodes ?: 0, // Manejo de campo opcional
        numberOfSeasons = this.numberOfSeasons ?: 0, // Manejo de campo opcional
        posterPath = this.posterPath?:"",
        backdropPath = this.backdropPath,
        popularity = this.popularity ?: 0.0,
        genres = this.genres?.map { it.toGenre() } ?: emptyList(), // Manejo de lista nula
        videos = this.videos?.toVideosResponse(),  // Conversión de Videos (puede ser nula)
        credits = this.credits?.toCreditsResponse()  // Conversión de Créditos (puede ser nula)
    )
}

fun TvGenreDto.toGenre(): TvGenre {
    return TvGenre(id = this.id, name = this.name)
}

fun TvVideosDto.toVideosResponse(): TvVideosResponse {
    return TvVideosResponse(results = this.results.map { it.toVideo() })
}

fun TvVideoDto.toVideo(): TvVideo {
    return TvVideo(
        key = this.key,
        name = this.name,
        site = this.site,
        type = this.type
    )
}

fun TvCreditsDto.toCreditsResponse(): TvCreditsResponse {
    return TvCreditsResponse(
        cast = this.cast.map { it.toCast() },
        crew = this.crew.map { it.toCrew() }
    )
}

fun TvCastDto.toCast(): TvCast {
    return TvCast(
        castId = this.castId,
        character = this.character,
        name = this.name,
        profilePath = this.profilePath,
        popularity = this.popularity
    )
}

fun TvCrewDto.toCrew(): TvCrew {
    return TvCrew(
        job = this.job,
        name = this.name,
        profilePath = this.profilePath
    )
}