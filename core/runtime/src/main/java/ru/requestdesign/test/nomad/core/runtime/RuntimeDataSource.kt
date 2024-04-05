package ru.requestdesign.test.nomad.core.runtime

import kotlinx.coroutines.flow.Flow
import ru.requestdesign.test.nomad.core.model.Product

interface RuntimeDataSource {
    val cartFlow: Flow<Map<Product, Int>>
    fun getSum(): Flow<Int>
    fun addProduct(product: Product)
    fun removeProduct(product: Product)
}