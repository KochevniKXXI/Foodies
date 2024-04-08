package ru.requestdesign.test.nomad.core.network

import ru.requestdesign.test.nomad.core.network.data.NetworkCategory
import ru.requestdesign.test.nomad.core.network.data.NetworkProduct
import ru.requestdesign.test.nomad.core.network.data.NetworkTag

/**
 * Интерфейс, предоставляющий запросы к серверу приложения.
 */
interface NetworkDatasource {
    suspend fun getCategories(): List<NetworkCategory>
    suspend fun getTags(): List<NetworkTag>
    suspend fun getProducts(): List<NetworkProduct>
    suspend fun getProductById(id: Int): NetworkProduct?
}