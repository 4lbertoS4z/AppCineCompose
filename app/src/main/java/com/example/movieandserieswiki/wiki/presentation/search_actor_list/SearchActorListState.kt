package com.example.movieandserieswiki.wiki.presentation.search_actor_list

import com.example.movieandserieswiki.wiki.presentation.models.ActorUi

data class SearchActorListState (
    val isLoading: Boolean = false,
    val actors: List<ActorUi> = emptyList(),
    val selectedActor: ActorUi? = null
)
