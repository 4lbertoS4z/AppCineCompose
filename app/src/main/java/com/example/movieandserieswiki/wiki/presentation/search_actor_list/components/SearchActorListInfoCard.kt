package com.example.movieandserieswiki.wiki.presentation.search_actor_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieandserieswiki.R
import com.example.movieandserieswiki.wiki.data.common.BASE_IMG_URL
import com.example.movieandserieswiki.wiki.presentation.models.ActorUi

@Composable
fun SearchActorListInfoCard(
    actorUi: ActorUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    val gradientColors = if (isSystemInDarkTheme()) {
        listOf(
            Color.Transparent.copy(alpha = 0.3f),
            Color.Black.copy(alpha = 0.5f),
            Color.Black.copy(alpha = 0.8f)
        )
    } else {
        listOf(
            Color.Transparent.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 0.8f)
        )
    }
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
            .clip(RoundedCornerShape(10))
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        // Cargar la imagen del actor
        AsyncImage(
            model = "${BASE_IMG_URL}${actorUi.profilePath}",
            contentDescription = actorUi.name,
            modifier = Modifier.size(220.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.sample), // Reemplaza con tu recurso de imagen
            error = painterResource(id = R.drawable.sample) // Reemplaza con tu recurso de imagen
        )

        // Fondo degradado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    Brush.verticalGradient(gradientColors)
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = actorUi.name,
                color = contentColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}