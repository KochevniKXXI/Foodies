package ru.requestdesign.test.nomad.feature.catalog

import ru.requestdesign.test.nomad.core.model.Product

interface CatalogUiState {
    data class Success(val cart: Map<Product, Int>) : CatalogUiState
    data object Empty : CatalogUiState
    data class Error(val error: Throwable) : CatalogUiState
    data object Loading : CatalogUiState
}