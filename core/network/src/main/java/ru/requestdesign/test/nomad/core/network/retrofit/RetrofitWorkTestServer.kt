package ru.requestdesign.test.nomad.core.network.retrofit

import ru.requestdesign.test.nomad.core.network.NetworkDataSource
import ru.requestdesign.test.nomad.core.network.data.NetworkCategory
import ru.requestdesign.test.nomad.core.network.data.NetworkProduct
import ru.requestdesign.test.nomad.core.network.data.NetworkTag
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RetrofitWorkTestServer @Inject constructor(
    private val networkService: WorkTestService
) : NetworkDataSource {
    override suspend fun getCategories(): List<NetworkCategory> = networkService.getCategories()

    override suspend fun getTags(): List<NetworkTag> = networkService.getTags()

    override suspend fun getProducts(): List<NetworkProduct> = networkService.getProducts()
}