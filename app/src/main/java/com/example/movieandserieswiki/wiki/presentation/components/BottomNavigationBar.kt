package com.example.movieandserieswiki.wiki.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Movie, contentDescription = "Movies") },
            label = { Text("Películas") },
            selected = false, // Cambia esto según si estás en la pantalla de películas
            onClick = {
                navController.navigate("movies") {
                    popUpTo("movies") { inclusive = true }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Tv, contentDescription = "Series") },
            label = { Text("Series") },
            selected = false, // Cambia esto según si estás en la pantalla de series
            onClick = {
                navController.navigate("series") {
                    popUpTo("movies") { inclusive = true }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Actores") },
            label = { Text("Actores") },
            selected = false, // Cambia esto según si estás en la pantalla de series
            onClick = {
                navController.navigate("series") {
                    popUpTo("movies") { inclusive = true }
                }
            }
        )
    }
}