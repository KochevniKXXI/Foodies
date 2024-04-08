package ru.requestdesign.test.nomad.core.data.repository

import ru.requestdesign.test.nomad.core.data.util.asInternalModel
import ru.requestdesign.test.nomad.core.model.Tag
import ru.requestdesign.test.nomad.core.network.NetworkDatasource
import javax.inject.Inject

/**
 * Реализация [TagsRepository], работающая с данными на сервере.
 */
class NetworkTagsRepository @Inject constructor (
    private val networkDataSource: NetworkDatasource
) : TagsRepository {
    override suspend fun getTags(): Result<List<Tag>> = runCatching {
        networkDataSource.getTags().map { it.asInternalModel() }
    }
}