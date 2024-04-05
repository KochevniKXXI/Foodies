package ru.requestdesign.test.nomad.feature.catalog

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
    val catalogUiState = productsRepository.getProducts().map { result ->
        result.fold(
            onSuccess = { productsList ->
                if (productsList.isNotEmpty()) {
                    UiState.Success(productsList)
                } else {
                    UiState.Empty
                }
            },
            onFailure = { UiState.Error }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState.Loading
    )

    //    private val _catalogUiState: MutableStateFlow<UiState<List<Product>>> = MutableStateFlow(UiState.Loading)
//    val catalogUiState = _catalogUiState.asStateFlow()
    private val _categoriesUiState: MutableStateFlow<UiState<List<Category>>> =
        MutableStateFlow(UiState.Loading)
    val categoriesUiState = _categoriesUiState.asStateFlow()

    init {
//        getProducts()
        getCategories()
    }

//    private fun getProducts() {
//        viewModelScope.launch {
//            runCatching {
//                productsRepository.getProducts()
//            }.onSuccess { productsList ->
//                _catalogUiState.value = if (productsList.isEmpty()) {
//                    UiState.Empty
//                } else {
//                    UiState.Success(productsList)
//                }
//            }.onFailure {
//                _catalogUiState.value = UiState.Error
//            }
//        }
//    }

    fun addProductInCart(product: Product) {
        productsRepository.addProductInCart(product)
    }

    fun removeProductFromCart(product: Product) {
        productsRepository.removeProductFromCart(product)
    }

    private fun getCategories() {
        viewModelScope.launch {
            runCatching {
                categoriesRepository.getCategories()
            }.onSuccess { categoriesList ->
                _categoriesUiState.value = if (categoriesList.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(categoriesList)
                }
            }.onFailure {
                _categoriesUiState.value = UiState.Error
            }
        }
    }
}