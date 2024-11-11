package com.example.movieandserieswiki.wiki.domain

import com.example.movieandserieswiki.core.domain.util.NetworkError
import com.example.movieandserieswiki.core.domain.util.Result

interface TvDataSource {
    suspend fun getPopularTv(page: Int): Result<List<Tv>, NetworkError>
    suspend fun getOnAirNowTv(page: Int): Result<List<Tv>, NetworkError>
    suspend fun getBestScoreTv(page: Int): Result<List<Tv>, NetworkError>
    suspend fun getDetailTv(tvId: Int): Result<Tv, NetworkError>
    suspend fun searchTv(query: String): Result<List<Tv>, NetworkError>
}