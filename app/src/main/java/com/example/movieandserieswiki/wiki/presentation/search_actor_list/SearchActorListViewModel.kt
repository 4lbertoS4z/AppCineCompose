package com.example.movieandserieswiki.wiki.presentation.search_actor_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieandserieswiki.core.domain.util.onError
import com.example.movieandserieswiki.core.domain.util.onSuccess
import com.example.movieandserieswiki.wiki.domain.ActorDataSource
import com.example.movieandserieswiki.wiki.presentation.models.ActorUi
import com.example.movieandserieswiki.wiki.presentation.models.MovieUi
import com.example.movieandserieswiki.wiki.presentation.models.toActorUi
import com.example.movieandserieswiki.wiki.presentation.models.toMovieUi
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchActorListViewModel(private val actorDataSource: ActorDataSource
) : ViewModel() {
    // Estado de búsqueda, almacena los actores encontrados por la búsqueda
    private val _searchState = MutableStateFlow<List<ActorUi>>(emptyList())
    val searchState = _searchState

    // Estado de la UI, como si está cargando, mostrando detalles, etc.
    private val _state = MutableStateFlow(SearchActorListState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SearchActorListState())

    // Canal de eventos para manejar errores
    private val _events = Channel<SearchActorListEvent>()
    val events = _events.receiveAsFlow()

    // Función para realizar la búsqueda de actores
    private fun searchActors(query: String) {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            actorDataSource.searchActor(query).onSuccess { actors ->
                _state.update { it.copy(isLoading = false) }
                // Mapear la lista de actores a ActorUi
                _searchState.value = actors.map { it.toActorUi() }
            }.onError { error ->
                _state.update { it.copy(isLoading = false) }
                _events.send(SearchActorListEvent.Error(error))
            }
        }
    }

    // Función para manejar las acciones de la vista
    fun onAction(action: SearchActorListAction) {
        when (action) {
            is SearchActorListAction.OnSearchQueryChanged -> {
                // Ejecutar búsqueda cuando cambia el texto de búsqueda
                searchActors(action.query)
            }

            is SearchActorListAction.OnActorSelected -> {
                // Cuando un actor es seleccionado, pasamos el id del actor al detalle
                navigateToActorDetail(action.actorUi)
            }
        }
    }
    private fun navigateToActorDetail(actorUi: ActorUi) {
        _state.update { it.copy(selectedActor = actorUi) }
        viewModelScope.launch {
            actorDataSource.getDetailActor(actorId = actorUi.id).onSuccess { selectedActor ->
                _state.update { currentState ->
                    currentState.copy(
                        selectedActor = selectedActor.toActorUi(),
                        isLoading = false
                    )
                }
            }.onError { error ->
                _events.send(SearchActorListEvent.Error(error))
            }
        }
    }
}