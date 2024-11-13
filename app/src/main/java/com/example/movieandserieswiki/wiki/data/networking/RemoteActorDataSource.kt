package com.example.movieandserieswiki.wiki.data.networking

import com.example.movieandserieswiki.BuildConfig
import com.example.movieandserieswiki.core.data.networking.safeCall
import com.example.movieandserieswiki.core.domain.util.NetworkError
import com.example.movieandserieswiki.core.domain.util.Result
import com.example.movieandserieswiki.core.domain.util.map
import com.example.movieandserieswiki.wiki.data.common.API_KEY
import com.example.movieandserieswiki.wiki.data.mappers.toActor
import com.example.movieandserieswiki.wiki.data.networking.dto.ActorDto
import com.example.movieandserieswiki.wiki.data.networking.dto.ActorResponseDto
import com.example.movieandserieswiki.wiki.domain.Actor
import com.example.movieandserieswiki.wiki.domain.ActorDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteActorDataSource(private val httpClient: HttpClient) : ActorDataSource {

    override suspend fun getDetailActor(actorId: Int): Result<Actor, NetworkError> {
        val url =
            "${BuildConfig.BASE_URL}person/$actorId?api_key=${API_KEY}&language=es-ES&append_to_response=combined_credits"
        return safeCall<ActorDto> { httpClient.get(url) }.map { response -> response.toActor() }
    }
    override suspend fun searchActor(query: String): Result<List<Actor>, NetworkError> {
        val url = "${BuildConfig.BASE_URL}search/person?api_key=${API_KEY}&language=es-ES&query=$query"
        return safeCall<ActorResponseDto> { httpClient.get(url) }
            .map { response -> response.results.map { it.toActor() } }
    }
}