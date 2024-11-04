package com.example.movieandserieswiki.wiki.presentation.actor_detail

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.movieandserieswiki.wiki.presentation.actor_detail.components.ActorInfoCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActorDetailScreen(
    actorId: Int,
    modifier: Modifier = Modifier,
    viewModel: ActorDetailViewModel = koinViewModel() // Usamos Koin para obtener el ViewModel
) {
    // Cargamos los detalles del actor
    LaunchedEffect(actorId) {
        viewModel.loadActorDetails(actorId)
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value // Suponiendo que el ViewModel tiene un state

    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.actorDetail != null) {
        val actor = state.actorDetail
        Log.d("ActorDetailScreen", "Cargando detalles del actor: ${actor.name}")

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            actor.profilePath?.let {
                item {
                    ActorInfoCard(
                        actorUi = actor,
                        onClick = { /* Acción al hacer clic, si es necesario */ },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                Text(
                    text = "Biografía:",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )
                Text(
                    text = actor.biography ?: "Biografía no disponible",
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
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