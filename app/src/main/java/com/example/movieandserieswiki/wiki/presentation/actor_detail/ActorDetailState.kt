package com.example.movieandserieswiki.wiki.presentation.actor_detail

import com.example.movieandserieswiki.wiki.presentation.models.ActorUi

data class ActorDetailState (
    val isLoading:Boolean = false,
    val actorDetail: ActorUi? = null
)