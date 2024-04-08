package ru.requestdesign.test.nomad.feature.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.requestdesign.test.nomad.core.data.repository.ProductsRepository
import ru.requestdesign.test.nomad.core.model.Product
import ru.requestdesign.test.nomad.feature.product.navigation.PRODUCT_ID_ARGUMENT
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productsRepository: ProductsRepository
) : ViewModel() {
    private val productId: Int = checkNotNull(savedStateHandle[PRODUCT_ID_ARGUMENT])
    val uiState = productsRepository.getProductById(productId).map { result ->
        result.fold(
            onSuccess = { pair ->
                pair?.let { ProductUiState.Success(it) } ?: ProductUiState.Empty
            },
            onFailure = { ProductUiState.Error(it) }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProductUiState.Loading
    )

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
}