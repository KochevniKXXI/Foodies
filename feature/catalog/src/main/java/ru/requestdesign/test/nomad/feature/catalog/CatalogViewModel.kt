package ru.requestdesign.test.nomad.feature.catalog

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.requestdesign.test.nomad.core.data.repository.CategoriesRepository
import ru.requestdesign.test.nomad.core.data.repository.ProductsRepository
import ru.requestdesign.test.nomad.core.model.Category
import ru.requestdesign.test.nomad.core.model.Product
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {
    private val _currentCategory = mutableStateOf<Category?>(null)
    val currentCategory = _currentCategory

    private val _categoriesUiState: MutableStateFlow<CategoriesUiState> =
        MutableStateFlow(CategoriesUiState.Loading)
    val categoriesUiState = _categoriesUiState.asStateFlow()

    val catalogUiState = productsRepository.getProducts().map { result ->
        result.fold(
            onSuccess = { cart ->
                if (cart.isNotEmpty()) {
                    CatalogUiState.Success(cart)
                } else {
                    CatalogUiState.Empty
                }
            },
            onFailure = { CatalogUiState.Error(it) }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CatalogUiState.Loading
    )

    init {
        getCategories()
    }

    fun updateCurrentCategory(category: Category) {
        if (_currentCategory.value == category) {
            _currentCategory.value = null
        } else {
            _currentCategory.value = category
        }
    }

    fun addProductInCart(product: Product) {
        viewModelScope.launch {
            productsRepository.addProductInCart(product)
        }
    }

    fun removeProductFromCart(product: Product) {
        viewModelScope.launch {
            productsRepository.removeProductFromCart(product)
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            categoriesRepository.getCategories()
                .onSuccess { categoriesList ->
                    _categoriesUiState.value = if (categoriesList.isEmpty()) {
                        CategoriesUiState.Empty
                    } else {
                        CategoriesUiState.Success(categoriesList)
                    }
                }
                .onFailure {
                    _categoriesUiState.value = CategoriesUiState.Error
                }
        }
    }
}