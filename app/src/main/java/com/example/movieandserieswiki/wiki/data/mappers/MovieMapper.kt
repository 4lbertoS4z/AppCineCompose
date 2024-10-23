package com.example.movieandserieswiki.wiki.data.mappers


import com.example.movieandserieswiki.wiki.data.networking.dto.CastDto
import com.example.movieandserieswiki.wiki.data.networking.dto.CreditsDto
import com.example.movieandserieswiki.wiki.data.networking.dto.CrewDto
import com.example.movieandserieswiki.wiki.data.networking.dto.GenreDto
import com.example.movieandserieswiki.wiki.data.networking.dto.MovieDto
import com.example.movieandserieswiki.wiki.data.networking.dto.VideoDto
import com.example.movieandserieswiki.wiki.data.networking.dto.VideosDto
import com.example.movieandserieswiki.wiki.domain.*

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        originalTitle = this.originalTitle ?: "Título no disponible", // Manejo de campo opcional
        overview = this.overview,
        voteAverage = this.voteAverage ?: 0.0, // Manejo de campo opcional
        voteCount = this.voteCount ?: 0, // Manejo de campo opcional
        releaseDate = this.releaseDate ?: "Fecha no disponible", // Manejo de campo opcional
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        popularity = this.popularity,
        genres = this.genres?.map { it.toGenre() } ?: emptyList(), // Manejo de lista nula
        runtime = this.runtime ?: 0, // Manejo de campo opcional
        videos = this.videos?.toVideosResponse(),  // Conversión de Videos (puede ser nula)
        credits = this.credits?.toCreditsResponse()  // Conversión de Créditos (puede ser nula)
    )
}

fun GenreDto.toGenre(): Genre {
    return Genre(id = this.id, name = this.name)
}

fun VideosDto.toVideosResponse(): VideosResponse {
    return VideosResponse(results = this.results.map { it.toVideo() })
}

fun VideoDto.toVideo(): Video {
    return Video(
        key = this.key,
        name = this.name,
        site = this.site,
        type = this.type
    )
}

fun CreditsDto.toCreditsResponse(): CreditsResponse {
    return CreditsResponse(
        cast = this.cast.map { it.toCast() },
        crew = this.crew.map { it.toCrew() }
    )
}

fun CastDto.toCast(): Cast {
    return Cast(
        castId = this.castId,
        character = this.character,
        name = this.name,
        profilePath = this.profilePath
    )
}

fun CrewDto.toCrew(): Crew {
    return Crew(
        job = this.job,
        name = this.name,
        profilePath = this.profilePath
    )
}