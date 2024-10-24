package com.example.movieandserieswiki.wiki.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
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

    Column(
        modifier = Modifier
            .padding(34.dp)
            .fillMaxWidth()
            .height(240.dp),
        horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
        verticalArrangement = Arrangement.Center // Centra verticalmente
    ) {
        // Utiliza AsyncImage para cargar la imagen
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movieUi.posterPath}",
            contentDescription = movieUi.title,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Hace que la imagen ocupe el espacio disponible
            contentScale = ContentScale.Crop, // Ajustar la escala de contenido
            placeholder = painterResource(id = R.drawable.sample), // Reemplaza con tu recurso de imagen
            error = painterResource(id = R.drawable.ic_launcher_background) // Reemplaza con tu recurso de imagen
        )

        // Texto debajo de la imagen
        Text(
            text = movieUi.title,
            color = contentColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(), maxLines = 2, overflow = TextOverflow.Ellipsis // Añade un espacio entre la imagen y el texto
        )
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
