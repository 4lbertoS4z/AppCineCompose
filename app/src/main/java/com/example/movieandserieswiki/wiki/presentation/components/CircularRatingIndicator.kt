package com.example.movieandserieswiki.wiki.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularRatingIndicator(rating: Float, modifier: Modifier = Modifier) {
    val progress = rating / 10f  // Escala de 0 a 10
    val color = when {
        rating >= 7 -> Color.Green
        rating >= 4 -> Color.Yellow
        else -> Color.Red
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Dibuja el fondo del círculo
            drawCircle(
                color = color.copy(alpha = 0.3f),
                style = Stroke(width = 4.dp.toPx())
            )
            // Dibuja el progreso
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = 8.dp.toPx())
            )
        }

        // Muestra el porcentaje
        Text(
            text = "${(rating * 10).toInt()}%",
            color = color,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}