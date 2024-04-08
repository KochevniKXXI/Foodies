package ru.requestdesign.test.nomad.foodies.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.requestdesign.test.nomad.foodies.navigation.FoodiesNavHost

@Composable
fun FoodiesApp(
    appState: FoodiesAppState
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        FoodiesNavHost(appState = appState)
    }
}