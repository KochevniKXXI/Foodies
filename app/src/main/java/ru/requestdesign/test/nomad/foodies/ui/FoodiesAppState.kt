package ru.requestdesign.test.nomad.foodies.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * remember-функция, которая запоминает и хранит состояние приложения.
 */
@Composable
fun rememberFoodiesAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
): FoodiesAppState {
    return remember(
        navController,
        windowSizeClass,
    ) {
        FoodiesAppState(
            navController = navController,
            windowSizeClass = windowSizeClass,
        )
    }
}

/**
 * Класс состояния приложения, который хранит [navController] и [windowSizeClass].
 */
class FoodiesAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
)