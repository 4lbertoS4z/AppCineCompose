package com.example.movieandserieswiki.wiki.presentation.models

import android.util.Log
import com.example.movieandserieswiki.wiki.domain.Actor
import java.text.NumberFormat
import java.util.Locale

data class ActorUi(
    val id: Int,
    val name: String,
    val biography: String,
    val birthday: String? = null,
    val deathday: String? = null,
    val placeOfBirth: String? = null,
    val profilePath: String? = null,
    val popularity: ActorDisplayableNumber,
    val cast: List<ActorCastUi> = emptyList(),
    val crew: List<ActorCrewUi> = emptyList()
)

data class ActorCastUi(
    val title: String? = null,
    val character: String,
    val releaseDate: String? = null,
    val posterPath: String? = null,
    val voteAverage: ActorDisplayableNumber,
    val overview: String,
    val mediaType: String // "movie" or "tv"
)

data class ActorCrewUi(
    val title: String? = null,
    val job: String,
    val releaseDate: String? = null,
    val posterPath: String? = null,
    val voteAverage: ActorDisplayableNumber,
    val overview: String,
    val mediaType: String // "movie" or "tv"
)

data class ActorDisplayableNumber(
    val value: Double,
    val formatted: String
)

fun Actor.toActorUi(): ActorUi {
    Log.d("ActorMapping", "Transforming Actor: id=$id, name=$name, popularity=$popularity, cast=$credits, crew=$credits")
    return ActorUi(
        id = id,
        name = name,
        biography = biography,
        birthday = birthday,
        deathday = deathday,
        placeOfBirth = placeOfBirth,
        profilePath = profilePath,
        popularity = popularity.toActorDisplayableNumber(),
        cast = credits?.cast?.map { ActorCastUi(
            title = it.title,
            character = it.character,
            releaseDate = it.releaseDate,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage.toActorDisplayableNumber(),
            overview = it.overview,
            mediaType = it.mediaType
        ) } ?: emptyList(),
        crew = credits?.crew?.map { ActorCrewUi(
            title = it.title,
            job = it.job,
            releaseDate = it.releaseDate,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage.toActorDisplayableNumber(),
            overview = it.overview,
            mediaType = it.mediaType
        ) } ?: emptyList()
    )
}

fun Double.toActorDisplayableNumber(): ActorDisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return ActorDisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}