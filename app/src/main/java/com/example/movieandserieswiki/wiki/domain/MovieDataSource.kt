package com.example.movieandserieswiki.wiki.domain

import com.example.movieandserieswiki.core.domain.util.NetworkError
import com.example.movieandserieswiki.core.domain.util.Result

interface MovieDataSource {
    suspend fun getPopularMovies(): Result<List<Movie>, NetworkError>
    suspend fun getUpcomingMovies(): Result<List<Movie>, NetworkError>
    suspend fun nowPlayingMovies(): Result<List<Movie>, NetworkError>
    suspend fun getDetailMovie(movieId:Int):Result<Movie,NetworkError>

}