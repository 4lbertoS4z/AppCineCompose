package com.example.movieandserieswiki.wiki.data.networking

import com.example.movieandserieswiki.BuildConfig
import com.example.movieandserieswiki.core.data.networking.safeCall
import com.example.movieandserieswiki.core.domain.util.NetworkError
import com.example.movieandserieswiki.core.domain.util.Result
import com.example.movieandserieswiki.core.domain.util.map
import com.example.movieandserieswiki.wiki.data.mappers.toTv
import com.example.movieandserieswiki.wiki.data.networking.dto.TvDto
import com.example.movieandserieswiki.wiki.data.networking.dto.TvResponseDto
import com.example.movieandserieswiki.wiki.domain.Tv
import com.example.movieandserieswiki.wiki.domain.TvDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteTvDataSource(private val httpClient: HttpClient) : TvDataSource {
    private val apiKey = "35e837b53acf4edd31521171a039d823"  // Obtén la API Key desde BuildConfig
    override suspend fun getPopularTv(page: Int): Result<List<Tv>, NetworkError> {
        val url = "${BuildConfig.BASE_URL}tv/popular?api_key=$apiKey&language=es-ES&page=$page"
        return safeCall<TvResponseDto> { httpClient.get(url) }.map { response -> response.results.map { it.toTv() } }
    }

    override suspend fun getOnAirNowTv(page: Int): Result<List<Tv>, NetworkError> {
        val url = "${BuildConfig.BASE_URL}tv/on_the_air?api_key=$apiKey&language=es-ES&page=$page"
        return safeCall<TvResponseDto> { httpClient.get(url) }.map { response -> response.results.map { it.toTv() } }
    }

    override suspend fun getBestScoreTv(page: Int): Result<List<Tv>, NetworkError> {
        val url = "${BuildConfig.BASE_URL}tv/top_rated?api_key=$apiKey&language=es-ES&page=$page"
        return safeCall<TvResponseDto> { httpClient.get(url) }.map { response -> response.results.map { it.toTv() } }
    }


    override suspend fun getDetailTv(tvId: Int): Result<Tv, NetworkError> {
        val url = "${BuildConfig.BASE_URL}tv/$tvId?&append_to_response=videos,credits&api_key=$apiKey&language=es-ES"
        return safeCall<TvDto> {httpClient.get(url)}.map { response -> response.toTv() } }

    }

