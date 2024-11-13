package com.example.movieandserieswiki.wiki.presentation.search_actor_list

import com.example.movieandserieswiki.wiki.presentation.models.ActorUi

sealed interface SearchActorListAction {
    data class OnActorSelected(val actorUi: ActorUi) : SearchActorListAction
    data class OnSearchQueryChanged(val query: String) : SearchActorListAction

}