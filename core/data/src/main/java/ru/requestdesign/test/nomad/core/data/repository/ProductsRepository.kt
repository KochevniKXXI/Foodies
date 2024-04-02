package ru.requestdesign.test.nomad.core.data.repository

import ru.requestdesign.test.nomad.core.model.Product

interface ProductsRepository {
    suspend fun getProducts(): List<Product>
}