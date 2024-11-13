package com.example.movieandserieswiki.wiki.presentation.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayer(videoKey: String, lifecycleOwner: LifecycleOwner, modifier: Modifier = Modifier) {
    // Usar remember para evitar recrear la vista cada vez que el composable se recomposite
    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                // Agregar el LifecycleObserver al YouTubePlayerView
                lifecycleOwner.lifecycle.addObserver(this)

                // Agregar el listener para cargar el video cuando esté listo
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        Log.d("YouTubePlayer", "Cargando video con key: $videoKey")
                        youTubePlayer.loadVideo(videoKey, 0f)
                        youTubePlayer.pause()
                    }
                })
            }
        },
        modifier = modifier
    )
}