package ru.requestdesign.test.nomad.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.requestdesign.test.nomad.core.model.Product

interface ProductsRepository {
    fun getProducts(): Flow<Result<Map<Product, Int>>>
    fun getCartSum(): Flow<Int>
    fun addProductInCart(product: Product)
    fun removeProductFromCart(product: Product)
}