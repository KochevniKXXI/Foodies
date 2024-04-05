package ru.requestdesign.test.nomad.feature.catalog

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data object Empty : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data object Error : UiState<Nothing>
}