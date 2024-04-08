package ru.requestdesign.test.nomad.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.requestdesign.test.nomad.core.model.Product

/**
 * Интерфейс для работы с [Product] и корзиной.
 */
interface ProductsRepository {
    fun getCart(): Flow<Map<Product, Int>>
    fun getProducts(): Flow<Result<Map<Product, Int>>>
    fun getProductById(id: Int): Flow<Result<Pair<Product, Int>?>>
    fun addProductInCart(product: Product)
    fun removeProductFromCart(product: Product)
}