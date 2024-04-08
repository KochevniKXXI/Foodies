package ru.requestdesign.test.nomad.feature.cart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.requestdesign.test.nomad.feature.cart.CartRoute

/**
 * Константа, необходимая для навигации к [CartRoute].
 */
private const val CART_ROUTE = "cart_route"

/**
 * Навигация к [CartRoute].
 */
fun NavController.navigateToCart(navOptions: NavOptions? = null) = navigate(CART_ROUTE, navOptions)

/**
 * Функция для внесения [CartRoute] в граф навигации.
 */
fun NavGraphBuilder.cartScreen(
    onUpClick: () -> Unit
) {
    composable(CART_ROUTE) {
        CartRoute(onUpClick = onUpClick)
    }
}