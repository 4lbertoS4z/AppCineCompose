package com.example.movieandserieswiki.wiki.presentation.movie_list

import com.example.movieandserieswiki.wiki.data.paging.MoviePagingSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.movieandserieswiki.core.domain.util.onError
import com.example.movieandserieswiki.core.domain.util.onSuccess
import com.example.movieandserieswiki.wiki.data.paging.MovieType
import com.example.movieandserieswiki.wiki.domain.Movie
import com.example.movieandserieswiki.wiki.domain.MovieDataSource
import com.example.movieandserieswiki.wiki.presentation.models.MovieUi
import com.example.movieandserieswiki.wiki.presentation.models.toMovieUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieListViewModel(
    private val movieDataSource: MovieDataSource
) : ViewModel() {

    val popularMovies: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(movieDataSource, MovieType.POPULAR) }
    ).flow.cachedIn(viewModelScope)

    val upcomingMovies: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(movieDataSource, MovieType.UPCOMING) }
    ).flow
        .map { pagingData ->
            pagingData.filter { movie ->
                filterUpcomingMovies(movie)
            }
        }
        .cachedIn(viewModelScope)

    val nowPlayingMovies: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(movieDataSource, MovieType.NOW_PLAYING) }
    ).flow.cachedIn(viewModelScope)

    private val _state = MutableStateFlow(MovieListState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MovieListState())
    private val _events = Channel<MovieListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: MovieListAction) {
        when (action) {
            is MovieListAction.OnMovieSelected -> {
                selectedMovie(action.movieUi)
            }
        }
    }

    private fun selectedMovie(movieUi: MovieUi) {
        _state.update { it.copy(movieDetail = movieUi) }
        viewModelScope.launch {
            movieDataSource.getDetailMovie(movieId = movieUi.id).onSuccess { movieDetail ->
                _state.update { currentState ->
                    currentState.copy(
                        movieDetail = movieDetail.toMovieUi(),
                        isLoading = false
                    )
                }
            }.onError { error ->
                _events.send(MovieListEvent.Error(error))
            }
        }
    }
    private fun filterUpcomingMovies(movie: Movie): Boolean {
        if (movie.releaseDate.isNullOrBlank()) return false
        return try {
            val currentDate = LocalDate.now()
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val releaseDate = LocalDate.parse(movie.releaseDate, dateFormatter)
            releaseDate.isAfter(currentDate)
        } catch (e: Exception) {
            false
        }
    }
}
