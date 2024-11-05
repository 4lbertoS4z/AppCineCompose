package com.example.movieandserieswiki.wiki.presentation.actor_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieandserieswiki.core.domain.util.onError
import com.example.movieandserieswiki.core.domain.util.onSuccess
import com.example.movieandserieswiki.wiki.domain.ActorDataSource
import com.example.movieandserieswiki.wiki.presentation.models.toActorUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActorDetailViewModel(
    private val actorDataSource: ActorDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(ActorDetailState())
    val state: StateFlow<ActorDetailState> = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        ActorDetailState()
    )

    private val _events = Channel<ActorDetailEvent>()
    val events: Flow<ActorDetailEvent> = _events.receiveAsFlow()

    // Función para cargar los detalles del actor directamente al recibir el actorId
    fun loadActorDetails(actorId: Int) {
        _state.value = ActorDetailState(isLoading = true) // Indica que se está cargando
        viewModelScope.launch {
            actorDataSource.getDetailActor(actorId).onSuccess { actorDetail ->
                Log.d("ActorDetailViewModel", "Actor detail: $actorDetail")
                _state.value = ActorDetailState(
                    actorDetail = actorDetail.toActorUi(),
                    isLoading = false
                )
            }.onError { error ->
                _state.value = ActorDetailState(isLoading = false) // Detiene la carga
                _events.send(ActorDetailEvent.Error(error))
            }
        }
    }
}