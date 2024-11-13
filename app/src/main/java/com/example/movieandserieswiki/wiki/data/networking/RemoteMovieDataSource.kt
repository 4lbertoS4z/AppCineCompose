package com.example.movieandserieswiki.wiki.data.networking

import com.example.movieandserieswiki.BuildConfig
import com.example.movieandserieswiki.core.data.networking.safeCall
import com.example.movieandserieswiki.core.domain.util.NetworkError
import com.example.movieandserieswiki.core.domain.util.Result
import com.example.movieandserieswiki.core.domain.util.map
import com.example.movieandserieswiki.wiki.data.common.API_KEY
import com.example.movieandserieswiki.wiki.data.mappers.toMovie
import com.example.movieandserieswiki.wiki.data.networking.dto.MovieDto
import com.example.movieandserieswiki.wiki.data.networking.dto.MoviesResponseDto
import com.example.movieandserieswiki.wiki.domain.Movie
import com.example.movieandserieswiki.wiki.domain.MovieDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteMovieDataSource(private val httpClient: HttpClient) : MovieDataSource {
    override suspend fun getPopularMovies(page: Int): Result<List<Movie>, NetworkError> {
        val url = "${BuildConfig.BASE_URL}movie/popular?api_key=${API_KEY}&language=es-ES&page=$page"
        return safeCall<MoviesResponseDto> { httpClient.get(url) }
            .map { response -> response.results.map { it.toMovie() } }
    }

    override suspend fun getUpcomingMovies(page: Int): Result<List<Movie>, NetworkError> {
        val url = "${BuildConfig.BASE_URL}movie/upcoming?api_key=${API_KEY}&language=es-ES&page=$page"
        return safeCall<MoviesResponseDto> { httpClient.get(url) }
            .map { response -> response.results.map { it.toMovie() } }
    }

    override suspend fun getNowPlayingMovies(page: Int): Result<List<Movie>, NetworkError> {
        val url =
            "${BuildConfig.BASE_URL}movie/now_playing?api_key=${API_KEY}&language=es-ES&page=$page"
        return safeCall<MoviesResponseDto> { httpClient.get(url) }
            .map { response -> response.results.map { it.toMovie() } }
    }

    override suspend fun getDetailMovie(movieId: Int): Result<Movie, NetworkError> {
        val url =
            "${BuildConfig.BASE_URL}movie/$movieId?&append_to_response=videos,credits&api_key=${API_KEY}&language=es-ES"
        return safeCall<MovieDto> { httpClient.get(url) }
            .map { response -> response.toMovie() }
    }
    override suspend fun searchMovie(query: String): Result<List<Movie>, NetworkError> {
        val url = "${BuildConfig.BASE_URL}search/movie?api_key=${API_KEY}&language=es-ES&query=$query"
        return safeCall<MoviesResponseDto> { httpClient.get(url) }
            .map { response -> response.results.map { it.toMovie() } }
    }
}