package ru.requestdesign.test.nomad.core.runtime

import kotlinx.coroutines.flow.Flow
import ru.requestdesign.test.nomad.core.model.Product

/**
 * Интерфейс, предоставляющий запросы к корзине товаров, существующей только во время выполнения.
 */
interface RuntimeDataSource {
    val cart: Flow<Map<Product, Int>>
    fun addProduct(product: Product)
    fun removeProduct(product: Product)
    fun getQuantityProductById(id: Int): Flow<Int>
}