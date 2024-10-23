package com.example.movieandserieswiki.wiki.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieandserieswiki.core.domain.util.onError
import com.example.movieandserieswiki.core.domain.util.onSuccess
import com.example.movieandserieswiki.wiki.domain.MovieDataSource
import com.example.movieandserieswiki.wiki.presentation.models.toMovieUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieDataSource: MovieDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(MovieListState())
    val state = _state.onStart { loadMovies() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MovieListState())
    private val _events = Channel<MovieListEvent>()
    val events = _events.receiveAsFlow()

    /* fun onAction(action:MovieListAction){
         when(action){
             is MovieListAction.OnMovieSelected -> {
                 selectedMovie(action.movieUi)
             }
         }
     } */


    private fun loadMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            movieDataSource.getMovies().onSuccess { movies ->
                _state.update {
                    it.copy(isLoading = false,
                        movies = movies.map { it.toMovieUi() }
                    )
                }
            }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(MovieListEvent.Error(error))
                }
        }
    }
}

