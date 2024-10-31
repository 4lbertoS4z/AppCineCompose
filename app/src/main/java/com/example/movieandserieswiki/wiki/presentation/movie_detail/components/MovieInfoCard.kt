package com.example.movieandserieswiki.wiki.presentation.movie_detail.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieandserieswiki.R
import com.example.movieandserieswiki.wiki.presentation.models.CastUi
import com.example.movieandserieswiki.wiki.presentation.models.GenreUi
import com.example.movieandserieswiki.wiki.presentation.components.CircularRatingIndicator

@Composable
fun MovieInfoCard(
    title: String,
    releaseDate: String,
    voteAverage: Double,
    overview: String,
    posterPath: String,
    genres: List<GenreUi>,
    cast: List<CastUi>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Blue.copy(alpha = 0.7f)
                    )
                )
            )
            .wrapContentSize(Alignment.Center)
    ) {
        // Imagen de portada
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500$posterPath",
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.sample),
            error = painterResource(id = R.drawable.ic_launcher_background),

            )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp, color = Color.White),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Puntuación:",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        CircularRatingIndicator(
            rating = voteAverage.toFloat(),
            modifier = Modifier
                .padding(start = 4.dp)
                .size(60.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "Género: ${genres.joinToString { it.name }}",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = "Fecha de estreno: $overview",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Fecha de estreno: $releaseDate",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            modifier = Modifier.padding(bottom = 4.dp)
        )



        LazyRow(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            items(cast) { actor ->
                Row(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Crear la URL de la imagen del actor
                    val profilePath =
                        actor.profilePath?.trimStart('/') // Eliminar el primer slash si existe
                    val imageUrl = if (!profilePath.isNullOrEmpty()) {
                        "https://image.tmdb.org/t/p/w500/$profilePath"
                    } else {
                        null // Manejar el caso de profilePath nulo o vacío
                    }

                    // Cargar la imagen del actor
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = actor.name,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.sample),
                        error = painterResource(id = R.drawable.ic_launcher_background),
                        onError = {
                            Log.e("ImageLoadError", "Failed to load image for ${actor.name}")
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = actor.name,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                        )
                        Text(
                            text = "como ${actor.character}",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                        )
                    }

                }
            }
        }

    }

}