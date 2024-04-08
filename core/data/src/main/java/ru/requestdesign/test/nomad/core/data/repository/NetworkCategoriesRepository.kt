package ru.requestdesign.test.nomad.core.data.repository

import ru.requestdesign.test.nomad.core.data.util.asInternalModel
import ru.requestdesign.test.nomad.core.model.Category
import ru.requestdesign.test.nomad.core.network.NetworkDatasource
import javax.inject.Inject

/**
 * Реализация [CategoriesRepository], работающая с данными на сервере.
 */
class NetworkCategoriesRepository @Inject constructor(
    private val networkDataSource: NetworkDatasource
) : CategoriesRepository {
    override suspend fun getCategories(): Result<List<Category>> = runCatching {
        networkDataSource.getCategories().map { it.asInternalModel() }
    }
}