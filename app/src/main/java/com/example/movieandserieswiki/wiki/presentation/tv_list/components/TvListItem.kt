package com.example.movieandserieswiki.wiki.presentation.tv_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieandserieswiki.R
import com.example.movieandserieswiki.wiki.presentation.components.CircularRatingIndicator
import com.example.movieandserieswiki.wiki.presentation.models.TvUi

@Composable
fun TvListItem(tvUi: TvUi, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(34.dp)
            .clip(RoundedCornerShape(10))
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        // Cargar la imagen del póster de la serie
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${tvUi.posterPath}",
            contentDescription = tvUi.name,
            modifier = Modifier.size(220.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.sample),
            error = painterResource(id = R.drawable.ic_launcher_background)
        )

        // Box con el fondo degradado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0f),
                            Color.Black.copy(alpha = 0.5f),
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            // Indicador de calificación
            CircularRatingIndicator(
                rating = tvUi.voteAverage.formatted.toFloat(),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
                    .size(60.dp)
            )
        }
    }
}