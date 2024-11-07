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
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListAction
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListEvent
import com.example.movieandserieswiki.wiki.presentation.movie_list.MovieListViewModel
import com.example.movieandserieswiki.wiki.presentation.search_list.SearchListScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptativeSearchList(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = koinViewModel(),
    movieListViewModel: MovieListViewModel = koinViewModel()
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
                SearchListScreen(
                    viewModel = viewModel,
                    state = state,
                    onAction = { action ->
                        when (action) {
                            is MovieListAction.OnSearchQueryChanged -> {
                                // Realizamos la búsqueda cada vez que cambia la consulta
                                movieListViewModel.onAction(action)
                            }
                            is MovieListAction.OnMovieSelected -> {
                                // Navegar a la pantalla de detalles de la película
                                navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                            }
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                // Aquí se mostraría el detalle de la película seleccionada
                // Si es necesario, puedes agregar una pantalla de detalle específica para la búsqueda
            }
        },
        modifier = modifier
    )
}