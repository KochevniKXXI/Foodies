package ru.requestdesign.test.nomad.core.data.repository

import ru.requestdesign.test.nomad.core.data.util.asInternalModel
import ru.requestdesign.test.nomad.core.model.Category
import ru.requestdesign.test.nomad.core.model.Product
import ru.requestdesign.test.nomad.core.network.NetworkDataSource
import ru.requestdesign.test.nomad.core.network.data.NetworkProduct
import javax.inject.Inject

internal class NetworkCategoriesRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : CategoriesRepository {
    override suspend fun getCategories(): List<Category> =
        networkDataSource.getCategories().map { it.asInternalModel() }

}