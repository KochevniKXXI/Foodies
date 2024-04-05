package ru.requestdesign.test.nomad.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import ru.requestdesign.test.nomad.core.data.util.asInternalModel
import ru.requestdesign.test.nomad.core.model.Product
import ru.requestdesign.test.nomad.core.network.NetworkDataSource
import ru.requestdesign.test.nomad.core.runtime.RuntimeDataSource
import javax.inject.Inject

internal class NetworkProductsRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val runtimeDataSource: RuntimeDataSource
) : ProductsRepository {
    override fun getProducts(): Flow<Result<Map<Product, Int>>> = combine(
        flow { emit(runCatching { networkDataSource.getProducts() }) },
        runtimeDataSource.cartFlow
    ) { result, cart ->
        result.map { networkProductsList ->
            networkProductsList
                .map { networkProduct -> networkProduct.asInternalModel() }
                .associateWith { product -> cart.getOrDefault(product, 0) }
        }
    }

    override fun getCartSum(): Flow<Int> = runtimeDataSource.getSum()

    override fun addProductInCart(product: Product) = runtimeDataSource.addProduct(product)

    override fun removeProductFromCart(product: Product) = runtimeDataSource.removeProduct(product)
}