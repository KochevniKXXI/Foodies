package ru.requestdesign.test.nomad.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import ru.requestdesign.test.nomad.core.data.util.asInternalModel
import ru.requestdesign.test.nomad.core.model.Product
import ru.requestdesign.test.nomad.core.network.NetworkDatasource
import ru.requestdesign.test.nomad.core.runtime.RuntimeDataSource
import javax.inject.Inject

/**
 * Реализация [ProductsRepository], работающая с данными на сервере и корзиной товаров времени выполнения.
 */
class NetworkProductsRepository @Inject constructor(
    private val networkDataSource: NetworkDatasource,
    private val runtimeDataSource: RuntimeDataSource
) : ProductsRepository {
    override fun getCart(): Flow<Map<Product, Int>> = runtimeDataSource.cart

    override fun getProducts(): Flow<Result<Map<Product, Int>>> = combine(
        flow { emit(runCatching { networkDataSource.getProducts() }) },
        getCart()
    ) { result, cart ->
        result.map { networkProductsList ->
            networkProductsList
                .map { networkProduct -> networkProduct.asInternalModel() }
                .associateWith { product -> cart.getOrDefault(product, 0) }
        }
    }

    override fun getProductById(id: Int): Flow<Result<Pair<Product, Int>?>> = combine(
        flow { emit(runCatching { networkDataSource.getProductById(id) }) },
        runtimeDataSource.getQuantityProductById(id)
    ) { result, quantity ->
        result.map { networkProduct ->
            networkProduct?.let {
                it.asInternalModel() to quantity
            }
        }
    }

    override fun addProductInCart(product: Product) = runtimeDataSource.addProduct(product)

    override fun removeProductFromCart(product: Product) = runtimeDataSource.removeProduct(product)
}