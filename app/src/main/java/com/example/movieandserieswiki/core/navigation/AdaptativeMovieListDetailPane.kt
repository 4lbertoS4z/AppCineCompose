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
import com.example.movieandserieswiki.core.presentation.util.ObserveAsEvents
import com.example.movieandserieswiki.core.presentation.util.toString
import com.example.movieandserieswiki.wiki.presentation.MovieListAction
import com.example.movieandserieswiki.wiki.presentation.MovieListEvent
import com.example.movieandserieswiki.wiki.presentation.MovieListScreen
import com.example.movieandserieswiki.wiki.presentation.MovieListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptativeMovieListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = koinViewModel()
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
                    state = state,
                   // onAction = { /* Aquí no se necesita ninguna acción */ },
                    modifier = modifier
                )
            }
        },
        detailPane = { /* Aquí no se necesita la vista de detalle aún */ },
        modifier = modifier
    )
}