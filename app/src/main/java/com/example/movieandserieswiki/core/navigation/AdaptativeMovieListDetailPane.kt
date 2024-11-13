package com.example.movieandserieswiki.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.movieandserieswiki.core.presentation.util.ObserveAsEvents
import com.example.movieandserieswiki.core.presentation.util.toString
import com.example.movieandserieswiki.wiki.presentation.actor_detail.ActorDetailViewModel
import com.example.movieandserieswiki.wiki.presentation.movie_detail.MovieDetailScreen
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListAction
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListEvent
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListScreen
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptativeMovieListDetailPane(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = koinViewModel(),
    actorViewModel: ActorDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is MovieListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                MovieListScreen(
                    viewModel = viewModel,
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when (action) {
                            is MovieListAction.OnMovieSelected -> {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )

                            }

                            is MovieListAction.OnSearchQueryChanged ->  viewModel.onAction(action)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                MovieDetailScreen(
                    state = state,
                    actorClicked = { actorId ->
                        // Navegar al detalle del actor y cargar detalles en ActorDetailViewModel
                        actorViewModel.loadActorDetails(actorId)

                        // Navegar usando el navController
                        navController.navigate("actor/${actorId}") // Usar la ruta del actor
                    })
            }
        },
        modifier = modifier


    )
}
