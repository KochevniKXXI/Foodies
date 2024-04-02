package ru.requestdesign.test.nomad.core.data.repository

import ru.requestdesign.test.nomad.core.data.util.asInternalModel
import ru.requestdesign.test.nomad.core.model.Product
import ru.requestdesign.test.nomad.core.network.NetworkDataSource
import javax.inject.Inject

internal class NetworkProductsRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : ProductsRepository {
    override suspend fun getProducts(): List<Product> =
        networkDataSource.getProducts().map { it.asInternalModel() }
}