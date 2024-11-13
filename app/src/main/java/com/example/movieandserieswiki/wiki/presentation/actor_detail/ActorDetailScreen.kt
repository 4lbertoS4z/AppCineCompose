package com.example.movieandserieswiki.wiki.presentation.actor_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.primaryContainerLightMediumContrast
import com.example.movieandserieswiki.wiki.presentation.actor_detail.components.ActorInfoCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActorDetailScreen(
    actorId: Int,
    modifier: Modifier = Modifier,
    viewModel: ActorDetailViewModel = koinViewModel() // Usamos Koin para obtener el ViewModel
) {
    val state =
        viewModel.state.collectAsStateWithLifecycle().value // Suponiendo que el ViewModel tiene un state

    // Llamar a loadActorDetails cuando se inicia la pantalla
    LaunchedEffect(actorId) {
        viewModel.loadActorDetails(actorId)
    }

    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryContainerLightMediumContrast)
    ) {


        if (state.isLoading) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.actorDetail != null) {
            val actor = state.actorDetail

            LazyColumn(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                actor.profilePath?.let {
                    item {
                        ActorInfoCard(
                            actorUi = actor,
                            cast = actor.cast,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                    }
                }


            }
        } else {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Detalles del actor no disponibles",
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor
                )
            }
        }
    }
}