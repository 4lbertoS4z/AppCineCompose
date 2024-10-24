package com.example.movieandserieswiki.wiki.presentation.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieandserieswiki.core.domain.util.onError
import com.example.movieandserieswiki.core.domain.util.onSuccess
import com.example.movieandserieswiki.wiki.domain.Movie
import com.example.movieandserieswiki.wiki.domain.MovieDataSource
import com.example.movieandserieswiki.wiki.presentation.models.MovieUi
import com.example.movieandserieswiki.wiki.presentation.models.toMovieUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieListViewModel(
    private val movieDataSource: MovieDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(MovieListState())

    val state = _state.onStart { loadAllMovies() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MovieListState())
    private val _events = Channel<MovieListEvent>()
    val events = _events.receiveAsFlow()

     fun onAction(action: MovieListAction){
         when(action){
             is MovieListAction.OnMovieSelected -> {
                 selectedMovie(action.movieUi)
             }
         }
     }

    private fun loadAllMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // Carga las películas populares
            movieDataSource.getPopularMovies().onSuccess { movies ->
                _state.update {
                    it.copy(isLoading = false,
                        popularMovies = movies.map { it.toMovieUi() }
                    )
                }
            }.onError { error ->
                _state.update { it.copy(isLoading = false) }
                _events.send(MovieListEvent.Error(error))
            }

            // Carga las películas próximas
            movieDataSource.getUpcomingMovies().onSuccess { upcomingMovies ->
                val filteredUpcomingMovies = filterUpcomingMovies(upcomingMovies)
                _state.update {
                    it.copy(isLoading = false,
                        upcomingMovies = filteredUpcomingMovies.map { it.toMovieUi() }
                    )
                }
            }.onError { error ->
                _state.update { it.copy(isLoading = false) }
                _events.send(MovieListEvent.Error(error))
            }

            // Carga las películas en cines
            movieDataSource.nowPlayingMovies().onSuccess { nowPlayingMovies ->
                _state.update {
                    it.copy(isLoading = false,
                        nowPlayingMovies = nowPlayingMovies.map { it.toMovieUi() } // Actualiza el estado de nowPlayingMovies
                    )
                }
            }.onError { error ->
                _state.update { it.copy(isLoading = false) }
                _events.send(MovieListEvent.Error(error))
            }
        }
    }

    private fun selectedMovie(movieUi: MovieUi) {
        _state.update { it.copy(movieDetail = movieUi) }

        viewModelScope.launch {
            movieDataSource.getDetailMovie(movieId = movieUi.id).onSuccess { movieDetail ->
                // Asegúrate de que movieDetail sea de tipo Movie
                _state.update { currentState ->
                    currentState.copy(
                        movieDetail = movieDetail.toMovieUi(),  // Asegúrate de que esto funcione correctamente
                        isLoading = false
                    )
                }
            }.onError { error ->
                _events.send(MovieListEvent.Error(error))
            }
        }
    }

    private fun filterUpcomingMovies(upcomingMovies: List<Movie>): List<Movie> {
        val currentDate = LocalDate.now()
        val dateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd") // Ajusta el patrón según el formato de tu fecha

        return upcomingMovies.filter { movie ->
            // Convierte la fecha de lanzamiento a LocalDate
            val releaseDate = LocalDate.parse(movie.releaseDate, dateFormatter)
            releaseDate.isAfter(currentDate) // Compara si la fecha de lanzamiento es después de la fecha actual
        }
    }


}
