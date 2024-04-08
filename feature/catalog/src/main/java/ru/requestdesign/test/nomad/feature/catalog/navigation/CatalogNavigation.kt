package ru.requestdesign.test.nomad.feature.catalog.navigation

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.requestdesign.test.nomad.feature.catalog.CatalogRoute

/**
 * Константа, необходимая для навигации к [CatalogRoute].
 */
const val CATALOG_ROUTE = "catalog_route"

/**
 * Функция для внесения [CatalogRoute] в граф навигации.
 */
fun NavGraphBuilder.catalogScreen(
    columns: GridCells,
    onProductClick: (Int) -> Unit,
    onCartClick: () -> Unit
) {
    composable(CATALOG_ROUTE) {
        CatalogRoute(
            columns = columns,
            onProductClick = onProductClick,
            onCartClick = onCartClick
        )
    }
}