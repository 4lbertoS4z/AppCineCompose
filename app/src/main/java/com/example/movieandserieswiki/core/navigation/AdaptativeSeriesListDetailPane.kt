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
import com.example.movieandserieswiki.wiki.presentation.tv_detail.TvDetailScreen
import com.example.movieandserieswiki.wiki.presentation.tv_list.TvListAction
import com.example.movieandserieswiki.wiki.presentation.tv_list.TvListEvent
import com.example.movieandserieswiki.wiki.presentation.tv_list.TvListScreen
import com.example.movieandserieswiki.wiki.presentation.tv_list.TvListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptativeSeriesListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: TvListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is TvListEvent.Error -> {
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
                TvListScreen(
                    viewModel = viewModel,
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when (action) {
                            is TvListAction.OnTvSelected -> {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )
                                
                            }
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                TvDetailScreen(
                    state = state
                )
            }
        },
        modifier = modifier
    )
}