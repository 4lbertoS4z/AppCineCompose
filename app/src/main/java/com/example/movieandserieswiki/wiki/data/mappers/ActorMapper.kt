package com.example.movieandserieswiki.wiki.data.mappers

import com.example.movieandserieswiki.wiki.data.networking.dto.ActorCastDto
import com.example.movieandserieswiki.wiki.data.networking.dto.ActorCreditsDto
import com.example.movieandserieswiki.wiki.data.networking.dto.ActorCrewDto
import com.example.movieandserieswiki.wiki.data.networking.dto.ActorDto
import com.example.movieandserieswiki.wiki.domain.Actor
import com.example.movieandserieswiki.wiki.domain.ActorCast
import com.example.movieandserieswiki.wiki.domain.ActorCreditsResponse
import com.example.movieandserieswiki.wiki.domain.ActorCrew

fun ActorDto.toActor(): Actor {
    return Actor(
        id = this.id,
        name = this.name,
        biography = this.biography,
        birthday = this.birthday ?: "Fecha de nacimiento no disponible", // Manejo de campo opcional
        deathday = this.deathday, // Este puede ser nulo
        placeOfBirth = this.placeOfBirth ?: "Lugar de nacimiento no disponible", // Manejo de campo opcional
        profilePath = this.profilePath,
        popularity = this.popularity ?: 0.0, // Manejo de campo opcional
        credits = this.credits?.toCreditsResponse() // Conversión de Créditos (puede ser nula)
    )
}

fun ActorCreditsDto.toCreditsResponse(): ActorCreditsResponse {
    return ActorCreditsResponse(
        cast = this.cast.map { it.toCast() },
        crew = this.crew.map { it.toCrew() }
    )
}

fun ActorCastDto.toCast(): ActorCast {

    return ActorCast(
        character = this.character,
        title = this.title ?: "",
        releaseDate = this.releaseDate,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage ?: 0.0, // Manejo de campo opcional
        overview = this.overview,
        mediaType = this.mediaType
    )
}

fun ActorCrewDto.toCrew(): ActorCrew {
    return ActorCrew(
        job = this.job,
        title = this.title?: "",
        releaseDate = this.releaseDate,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage ?: 0.0, // Manejo de campo opcional
        overview = this.overview,
        mediaType = this.mediaType
    )
}