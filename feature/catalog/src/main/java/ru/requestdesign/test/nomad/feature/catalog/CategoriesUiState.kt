package ru.requestdesign.test.nomad.feature.catalog

import ru.requestdesign.test.nomad.core.model.Category

interface CategoriesUiState {
    data class Success(val categories: List<Category>) : CategoriesUiState
    data object Empty : CategoriesUiState
    data object Error : CategoriesUiState
    data object Loading : CategoriesUiState
}