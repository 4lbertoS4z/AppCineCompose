package com.example.movieandserieswiki.wiki.presentation.tv_list

import com.example.movieandserieswiki.wiki.presentation.models.TvUi

sealed interface TvListAction {
    data class OnTvSelected(val tvUi: TvUi) : TvListAction
    data class OnSearchQueryChanged(val query: String) : TvListAction
}