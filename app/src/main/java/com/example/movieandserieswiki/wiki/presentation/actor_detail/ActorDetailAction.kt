package com.example.movieandserieswiki.wiki.presentation.actor_detail

sealed class ActorDetailAction {
    data class OnActorSelected(val actorId: Int) : ActorDetailAction()
}