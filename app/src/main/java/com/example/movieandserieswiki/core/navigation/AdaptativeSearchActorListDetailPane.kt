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
import com.example.movieandserieswiki.wiki.presentation.actor_detail.ActorDetailScreen
import com.example.movieandserieswiki.wiki.presentation.search_actor_list.SearchActorListAction
import com.example.movieandserieswiki.wiki.presentation.search_actor_list.SearchActorListEvent
import com.example.movieandserieswiki.wiki.presentation.search_actor_list.SearchActorListScreen
import com.example.movieandserieswiki.wiki.presentation.search_actor_list.SearchActorListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptativeSearchActorListDetailPane(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SearchActorListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Observa los eventos para manejar errores, etc.
    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is SearchActorListEvent.Error -> {
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
                // Asegúrate de manejar la acción de selección de actor
                SearchActorListScreen(
                    viewModel = viewModel,
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when (action) {
                            is SearchActorListAction.OnActorSelected -> {
                                // Navegar a la pantalla de detalle del actor
                                navigator.navigateTo(pane = ListDetailPaneScaffoldRole.Detail)
                            }
                            is SearchActorListAction.OnSearchQueryChanged -> viewModel.onAction(action)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                ActorDetailScreen(
                    actorId = state.selectedActor?.id ?: 0
                )
            }
        },
        modifier = modifier
    )
}