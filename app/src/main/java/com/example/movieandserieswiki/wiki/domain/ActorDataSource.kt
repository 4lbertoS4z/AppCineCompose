package com.example.movieandserieswiki.wiki.domain

import com.example.movieandserieswiki.core.domain.util.NetworkError
import com.example.movieandserieswiki.core.domain.util.Result

interface ActorDataSource {
    suspend fun getDetailActor(actorId: Int): Result<Actor, NetworkError>
    suspend fun searchActor(query: String): Result<List<Actor>, NetworkError>
}