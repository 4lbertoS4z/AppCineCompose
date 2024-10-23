package com.example.movieandserieswiki.wiki.domain

import com.example.movieandserieswiki.core.domain.util.NetworkError
import com.example.movieandserieswiki.core.domain.util.Result

interface MovieDataSource {
    suspend fun getMovies(): Result<List<Movie>, NetworkError>
}