package ru.requestdesign.test.nomad.core.network.retrofit

import ru.requestdesign.test.nomad.core.network.NetworkDatasource
import ru.requestdesign.test.nomad.core.network.data.NetworkCategory
import ru.requestdesign.test.nomad.core.network.data.NetworkProduct
import ru.requestdesign.test.nomad.core.network.data.NetworkTag
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Источник данных, взаимодействующий с сервером через Retrofit.
 */
@Singleton
internal class RetrofitWorkTestServer @Inject constructor(
    private val networkService: WorkTestService
) : NetworkDatasource {
    override suspend fun getCategories(): List<NetworkCategory> = networkService.getCategories()

    override suspend fun getTags(): List<NetworkTag> = networkService.getTags()

    override suspend fun getProducts(): List<NetworkProduct> = networkService.getProducts()
    override suspend fun getProductById(id: Int): NetworkProduct? = getProducts().firstOrNull { it.id == id }
}