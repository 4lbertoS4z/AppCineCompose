package com.example.movieandserieswiki.wiki.domain

import com.example.movieandserieswiki.core.domain.util.NetworkError
import com.example.movieandserieswiki.core.domain.util.Result

interface MovieDataSource {
    suspend fun getPopularMovies(page: Int): Result<List<Movie>, NetworkError>
    suspend fun getUpcomingMovies(page: Int): Result<List<Movie>, NetworkError>
    suspend fun nowPlayingMovies(page: Int): Result<List<Movie>, NetworkError>
    suspend fun getDetailMovie(movieId: Int): Result<Movie, NetworkError>
}