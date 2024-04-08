package ru.requestdesign.test.nomad.foodies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import dagger.hilt.android.AndroidEntryPoint
import ru.requestdesign.test.nomad.core.designsystem.theme.FoodiesTheme
import ru.requestdesign.test.nomad.foodies.ui.FoodiesApp
import ru.requestdesign.test.nomad.foodies.ui.rememberFoodiesAppState

@ExperimentalMaterial3WindowSizeClassApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodiesTheme {
                val appState = rememberFoodiesAppState(
                    windowSizeClass = calculateWindowSizeClass(
                        activity = this
                    )
                )
                FoodiesApp(appState = appState)
            }
        }
    }
}