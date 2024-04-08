package ru.requestdesign.test.nomad.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.requestdesign.test.nomad.core.data.repository.ProductsRepository
import ru.requestdesign.test.nomad.core.model.Product
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {
    val cartFlow = productsRepository.getCart().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyMap()
    )

    fun addProductInCart(product: Product) = productsRepository.addProductInCart(product)

    fun removeProductFromCart(product: Product) = productsRepository.removeProductFromCart(product)
}