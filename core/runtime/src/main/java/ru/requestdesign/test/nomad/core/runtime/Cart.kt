package ru.requestdesign.test.nomad.core.runtime

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.requestdesign.test.nomad.core.model.Product
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Реализация корзины товаров, существующей только во время выполнения.
 */
@Singleton
internal class Cart @Inject constructor() : RuntimeDataSource {
    private val productsMap = mutableMapOf<Product, Int>()
    private val cartFlow = MutableSharedFlow<Map<Product, Int>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override val cart: Flow<Map<Product, Int>> = cartFlow.onStart { emit(productsMap.toMap()) }

    override fun addProduct(product: Product) {
        productsMap.merge(product, 1) { oldValue, _ ->
            oldValue + 1
        }
        coroutineScope.launch {
            cartFlow.emit(productsMap.toMap())
        }
    }

    override fun removeProduct(product: Product) {
        productsMap.merge(product, 0) { oldValue, _ ->
            if (oldValue <= 1) {
                null
            } else {
                oldValue - 1
            }
        }
        coroutineScope.launch {
            cartFlow.emit(productsMap.toMap())
        }
    }

    override fun getQuantityProductById(id: Int): Flow<Int> = cart.map { cart ->
        cart.mapKeys { it.key.id }.getOrDefault(id, 0)
    }
}