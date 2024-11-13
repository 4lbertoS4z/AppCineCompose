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
import androidx.compose.material3.Text
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
import com.example.movieandserieswiki.wiki.data.common.BASE_IMG_URL
import com.example.movieandserieswiki.wiki.presentation.components.CircularRatingIndicator
import com.example.movieandserieswiki.wiki.presentation.models.TvUi

@Composable
fun TvListInfoCard(tvUi: TvUi, onClick: () -> Unit, modifier: Modifier = Modifier) {
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
            model = "${BASE_IMG_URL}${tvUi.posterPath}",
            contentDescription = tvUi.name,
            modifier = Modifier.size(220.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.sample),
            error = painterResource(id = R.drawable.sample)
        )
    }
}