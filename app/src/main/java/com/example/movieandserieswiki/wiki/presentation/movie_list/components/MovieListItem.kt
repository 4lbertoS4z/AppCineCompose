package com.example.movieandserieswiki.wiki.presentation.movie_list.components

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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieandserieswiki.R
import com.example.movieandserieswiki.wiki.presentation.models.MovieUi

@Composable
fun MovieListItem(movieUi: MovieUi, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Box(
        modifier = Modifier.clickable(onClick = onClick)
            .padding(34.dp)
            .clip(RoundedCornerShape(10))
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Utiliza AsyncImage para cargar la imagen
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movieUi.posterPath}",
            contentDescription = movieUi.title,
            modifier = Modifier.size(220.dp),
            contentScale = ContentScale.Crop, // Ajustar la escala de contenido
            placeholder = painterResource(id = R.drawable.sample), // Reemplaza con tu recurso de imagen
            error = painterResource(id = R.drawable.ic_launcher_background) // Reemplaza con tu recurso de imagen
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0f),
                            Color.Black.copy(alpha = 0.5f),
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = movieUi.voteAverage.formatted, color = contentColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}



