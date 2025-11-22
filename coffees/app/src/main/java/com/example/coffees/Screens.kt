package com.example.coffees

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreenLayout() {
    ScreenContent(title = "Home Screen")
}

@Composable
fun FavoritesScreen() {
    ScreenContent(title = "Favorite Screen")
}

@Composable
fun CartsScreen() {
    ScreenContent(title = "Cart Screen")
}

@Composable
fun ProfilerScreen() {
    ScreenContent(title = "Profile Screen")
}

@Composable
fun NotificationScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.notfi),
            contentDescription = "Notify image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}


@Composable
fun ScreenContent(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
