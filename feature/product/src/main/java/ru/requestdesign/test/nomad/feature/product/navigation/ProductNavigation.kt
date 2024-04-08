package ru.requestdesign.test.nomad.feature.product.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.requestdesign.test.nomad.feature.product.ProductRoute

/**
 * Константы, необходимые для навигации к [ProductRoute].
 */
private const val PRODUCT_ROUTE = "product_route"
internal const val PRODUCT_ID_ARGUMENT = "product_id"
private const val PRODUCT_ROUTE_WITH_ARGUMENT = "$PRODUCT_ROUTE/{$PRODUCT_ID_ARGUMENT}"

/**
 * Навигация к [ProductRoute].
 */
fun NavController.navigateToProduct(id: Int, navOptions: NavOptions? = null) =
    navigate("$PRODUCT_ROUTE/$id", navOptions)

/**
 * Функция для внесения [ProductRoute] в граф навигации.
 */
fun NavGraphBuilder.productScreen(
    shouldShowExpandedLayout: Boolean,
    onUpClick: () -> Unit
) {
    composable(
        route = PRODUCT_ROUTE_WITH_ARGUMENT,
        arguments = listOf(
            navArgument(PRODUCT_ID_ARGUMENT) {
                type = NavType.IntType
            }
        )
    ) {
        ProductRoute(
            shouldShowExpandedLayout = shouldShowExpandedLayout,
            onUpClick = onUpClick
        )
    }
}