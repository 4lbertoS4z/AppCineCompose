package com.example.movieandserieswiki.wiki.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieandserieswiki.R
import com.example.movieandserieswiki.ui.theme.MovieAndSeriesWikiTheme
import com.example.movieandserieswiki.wiki.domain.Movie

@Composable
fun MovieListItem(movieUi: com.example.movieandserieswiki.wiki.presentation.models.MovieUi, modifier: Modifier = Modifier) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Utiliza AsyncImage para cargar la imagen
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w200${movieUi.posterPath}",
            contentDescription = movieUi.title,
            modifier = Modifier.size(85.dp),
            contentScale = ContentScale.Crop, // Ajustar la escala de contenido
            placeholder = painterResource(id = R.drawable.sample), // Reemplaza con tu recurso de imagen
            error = painterResource(id = R.drawable.ic_launcher_background) // Reemplaza con tu recurso de imagen
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movieUi.title, fontSize = 20.sp,
                fontWeight = FontWeight.Bold, color = contentColor
            )
        }
    }
}



fun Movie.toMovieUi(): MovieUi {
    return MovieUi(
        id = id,
        title = title,
        posterPath = posterPath ?: "",  // Asegúrate de manejar null adecuadamente
        releaseDate = releaseDate
    )
}

// Clase MovieUi simplificada
data class MovieUi(
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String
)
