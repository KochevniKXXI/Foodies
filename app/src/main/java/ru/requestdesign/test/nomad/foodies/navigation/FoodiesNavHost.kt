package ru.requestdesign.test.nomad.foodies.navigation

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import ru.requestdesign.test.nomad.feature.cart.navigation.cartScreen
import ru.requestdesign.test.nomad.feature.cart.navigation.navigateToCart
import ru.requestdesign.test.nomad.feature.catalog.navigation.CATALOG_ROUTE
import ru.requestdesign.test.nomad.feature.catalog.navigation.catalogScreen
import ru.requestdesign.test.nomad.feature.product.navigation.navigateToProduct
import ru.requestdesign.test.nomad.feature.product.navigation.productScreen
import ru.requestdesign.test.nomad.foodies.ui.FoodiesAppState

/**
 * Навигационный граф верхнего уровня.
 */
@Composable
fun FoodiesNavHost(
    appState: FoodiesAppState,
    modifier: Modifier = Modifier,
    startDestination: String = CATALOG_ROUTE,
) {
    val navController = appState.navController
    val windowSizeClass = appState.windowSizeClass
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        catalogScreen(
            columns = if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
                GridCells.Fixed(4)
            } else {
                GridCells.Fixed(2)
            },
            onProductClick = { navController.navigateToProduct(it) },
            onCartClick = navController::navigateToCart
        )
        productScreen(
            shouldShowExpandedLayout = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded,
            onUpClick = navController::navigateUp
        )
        cartScreen { navController.navigateUp() }
    }
}