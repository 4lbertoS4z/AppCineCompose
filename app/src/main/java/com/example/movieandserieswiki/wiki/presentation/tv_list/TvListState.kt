package com.example.movieandserieswiki.wiki.presentation.tv_list

import com.example.movieandserieswiki.wiki.presentation.models.TvUi

data class TvListState (
    val isLoading: Boolean = false,
    val popularTvs: List<TvUi> = emptyList(),
    val onAirNowTvs: List<TvUi> = emptyList(),
    val bestScoreTvs: List<TvUi> = emptyList(),
    val tvDetail: TvUi? = null,
    val currentPage: Int = 1

    )