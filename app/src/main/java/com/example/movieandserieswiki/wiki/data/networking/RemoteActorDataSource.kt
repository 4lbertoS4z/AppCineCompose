package com.example.movieandserieswiki.wiki.data.networking

import com.example.movieandserieswiki.BuildConfig
import com.example.movieandserieswiki.core.data.networking.safeCall
import com.example.movieandserieswiki.core.domain.util.NetworkError
import com.example.movieandserieswiki.core.domain.util.Result
import com.example.movieandserieswiki.core.domain.util.map
import com.example.movieandserieswiki.wiki.data.mappers.toActor
import com.example.movieandserieswiki.wiki.data.networking.dto.ActorDto
import com.example.movieandserieswiki.wiki.domain.Actor
import com.example.movieandserieswiki.wiki.domain.ActorDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteActorDataSource(private val httpClient: HttpClient) : ActorDataSource {
    private val apiKey = "35e837b53acf4edd31521171a039d823"  // Obtén la API Key desde BuildConfig
    override suspend fun getDetailActor(actorId: Int): Result<Actor, NetworkError> {
        val url = "${BuildConfig.BASE_URL}person/$actorId?api_key=$apiKey&language=es-ES&append_to_response=combined_credits"
        return safeCall<ActorDto> { httpClient.get(url) }.map { response -> response.toActor() }

    }
}