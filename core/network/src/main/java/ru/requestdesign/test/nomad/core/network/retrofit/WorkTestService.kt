package ru.requestdesign.test.nomad.core.network.retrofit

import retrofit2.http.GET
import ru.requestdesign.test.nomad.core.network.data.NetworkCategory
import ru.requestdesign.test.nomad.core.network.data.NetworkProduct
import ru.requestdesign.test.nomad.core.network.data.NetworkTag

/**
 * API-сервис, взаимодействующий с сервером.
 */
internal interface WorkTestService {
    @GET("Categories.json")
    suspend fun getCategories(): List<NetworkCategory>

    @GET("Tags.json")
    suspend fun getTags(): List<NetworkTag>

    @GET("Products.json")
    suspend fun getProducts(): List<NetworkProduct>
}