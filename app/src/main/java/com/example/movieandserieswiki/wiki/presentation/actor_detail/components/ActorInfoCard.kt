package com.example.movieandserieswiki.wiki.presentation.actor_detail.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieandserieswiki.R
import com.example.movieandserieswiki.wiki.data.common.BASE_IMG_URL
import com.example.movieandserieswiki.wiki.presentation.models.ActorCastUi
import com.example.movieandserieswiki.wiki.presentation.models.ActorUi

@Composable
fun ActorInfoCard(actorUi: ActorUi, cast: List<ActorCastUi>, modifier: Modifier = Modifier) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent, Color.Blue.copy(alpha = 0.7f)
                    )
                )
            )
            .wrapContentSize(Alignment.Center)
    ) {
        // Utiliza AsyncImage para cargar la imagen del actor
        AsyncImage(
            model = "${BASE_IMG_URL}${actorUi.profilePath}",
            contentDescription = actorUi.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Fit, // Ajustar la escala de contenido
            placeholder = painterResource(id = R.drawable.sample), // Reemplaza con tu recurso de imagen
            error = painterResource(id = R.drawable.sample) // Reemplaza con tu recurso de imagen
        )
        // Muestra el nombre del actor
        Text(
            text = actorUi.name,
            color = contentColor,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Start
        )
        Text(
            text = "Fecha de nacimiento: ${actorUi.birthday ?: ""}",
            color = contentColor,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Start
        )

        Text(
            text = actorUi.placeOfBirth ?: "Lugar de nacimiento no disponible",
            color = contentColor,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Start
        )
        Text(
            text = "Fecha de Fallecimiento: ${actorUi.deathday ?: ""}",
            color = contentColor,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Start
        )

        Text(
            text = "Biografia: ${actorUi.biography ?: ""}",
            color = contentColor,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            items(cast) { actor ->
                Row(
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {

                    Box(
                        modifier = Modifier.size(150.dp) // Tamaño del contenedor
                    ) {
                        // Cargar la imagen de la película
                        AsyncImage(model = "${BASE_IMG_URL}${actor.posterPath}",
                            contentDescription = actor.title,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.sample),
                            error = painterResource(id = R.drawable.sample),
                            onError = {
                                Log.e("ImageLoadError", "Failed to load image for ${actor.title}")
                            }

                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        if (actor.title.isNullOrEmpty()) {
                            Text(
                                text = "No hay información disponible",
                                style = MaterialTheme.typography.bodySmall.copy(color = contentColor),
                            )
                        } else {
                            Text(
                                text = actor.title,
                                style = MaterialTheme.typography.bodyMedium.copy(color = contentColor),
                            )
                        }

                        if (actor.character.isNullOrEmpty()) {
                            Text(
                                text = "No hay información disponible",
                                style = MaterialTheme.typography.bodySmall.copy(color = contentColor),
                            )
                        } else {
                            Text(
                                text = "como ${actor.character}",
                                style = MaterialTheme.typography.bodySmall.copy(color = contentColor),
                            )
                        }
                        if (actor.releaseDate.isNullOrEmpty()) {
                            Text(
                                text = "No hay información disponible",
                                style = MaterialTheme.typography.bodySmall.copy(color = contentColor),
                            )
                        } else {
                            Text(
                                text = "${actor.releaseDate}",
                                style = MaterialTheme.typography.bodySmall.copy(color = contentColor),
                            )
                        }
                        if (actor.mediaType.isNullOrEmpty()) {
                            Text(
                                text = "No hay información disponible",
                                style = MaterialTheme.typography.bodySmall.copy(color = contentColor),
                            )
                        } else {
                            Text(
                                text = "${actor.mediaType}",
                                style = MaterialTheme.typography.bodySmall.copy(color = contentColor),
                            )
                        }
                    }
                }
            }
        }
    }
}