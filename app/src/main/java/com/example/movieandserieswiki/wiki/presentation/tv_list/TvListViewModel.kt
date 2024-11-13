package com.example.movieandserieswiki.wiki.presentation.tv_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieandserieswiki.core.domain.util.onError
import com.example.movieandserieswiki.core.domain.util.onSuccess
import com.example.movieandserieswiki.wiki.data.paging.TvPagingSource
import com.example.movieandserieswiki.wiki.data.paging.TvType
import com.example.movieandserieswiki.wiki.domain.Tv
import com.example.movieandserieswiki.wiki.domain.TvDataSource
import com.example.movieandserieswiki.wiki.presentation.models.MovieUi
import com.example.movieandserieswiki.wiki.presentation.models.TvUi
import com.example.movieandserieswiki.wiki.presentation.models.toTvUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TvListViewModel(private val tvDataSource: TvDataSource) : ViewModel() {

    val popularTvs: Flow<PagingData<Tv>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { TvPagingSource(tvDataSource, TvType.POPULAR) }
    ).flow.cachedIn(viewModelScope)
    val topRatedTvs: Flow<PagingData<Tv>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { TvPagingSource(tvDataSource, TvType.BEST_SCORE) }
    ).flow.cachedIn(viewModelScope)

    val onAirTvs: Flow<PagingData<Tv>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { TvPagingSource(tvDataSource, TvType.ON_AIR_NOW) }
    ).flow.cachedIn(viewModelScope)

    private val _state = MutableStateFlow(TvListState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), TvListState())
    private val _events = Channel<TvListEvent>()
    val events = _events.receiveAsFlow()
    private val _searchState = MutableStateFlow<List<TvUi>>(emptyList())
    val searchState = _searchState

    private fun searchTvs(query: String){
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            tvDataSource.searchTv(query).onSuccess { tvs ->
                _state.update { it.copy(isLoading = false) }
                _searchState.value = tvs.map { it.toTvUi() }
            }.onError { error ->
                _state.update { it.copy(isLoading = false) }
                _events.send(TvListEvent.Error(error))
            }
        }
    }


    fun onAction(action: TvListAction) {
        when (action) {
            is TvListAction.OnTvSelected -> {
                selectedTv(action.tvUi)
            }
            is TvListAction.OnSearchQueryChanged -> {
                searchTvs(action.query)
            }
        }
    }

    private fun selectedTv(tvUi: TvUi) {
        _state.update { it.copy(tvDetail = tvUi) }
        viewModelScope.launch {
            tvDataSource.getDetailTv(tvId = tvUi.id).onSuccess { tvDetail ->
                _state.update { currentState ->
                    currentState.copy(
                        tvDetail = tvDetail.toTvUi(),
                        isLoading = false
                    )
                }
            }.onError { error ->
                _events.send(TvListEvent.Error(error))
            }
        }
    }
}

