package com.example.movieandserieswiki.di

import com.example.movieandserieswiki.core.data.networking.HttpClientFactory
import com.example.movieandserieswiki.wiki.domain.MovieDataSource
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.dsl.bind
import com.example.movieandserieswiki.wiki.data.networking.RemoteMovieDataSource
import com.example.movieandserieswiki.wiki.data.networking.RemoteTvDataSource
import com.example.movieandserieswiki.wiki.data.networking.RemoteActorDataSource
import com.example.movieandserieswiki.wiki.domain.ActorDataSource
import com.example.movieandserieswiki.wiki.domain.TvDataSource
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListViewModel
import com.example.movieandserieswiki.wiki.presentation.tv_list.TvListViewModel
import com.example.movieandserieswiki.wiki.presentation.actor_detail.ActorDetailViewModel


val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteMovieDataSource).bind<MovieDataSource>()
    singleOf(::RemoteTvDataSource).bind<TvDataSource>()
    singleOf(::RemoteActorDataSource).bind<ActorDataSource>()
    viewModelOf(::MovieListViewModel)
    viewModelOf(::TvListViewModel)
    viewModelOf(::ActorDetailViewModel)
}