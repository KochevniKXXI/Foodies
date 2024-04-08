package ru.requestdesign.test.nomad.feature.product

import ru.requestdesign.test.nomad.core.model.Product

interface ProductUiState {
    data class Success(val productWithQuantity: Pair<Product, Int>) : ProductUiState
    data object Empty : ProductUiState
    data object Loading : ProductUiState
    data class Error(val error: Throwable) : ProductUiState
}