package ru.requestdesign.test.nomad.core.data.repository

import ru.requestdesign.test.nomad.core.data.util.asInternalModel
import ru.requestdesign.test.nomad.core.model.Tag
import ru.requestdesign.test.nomad.core.network.NetworkDataSource
import javax.inject.Inject

class NetworkTagsRepository @Inject constructor (
    private val networkDataSource: NetworkDataSource
) : TagsRepository {
    override suspend fun getTags(): List<Tag> =
        networkDataSource.getTags().map { it.asInternalModel() }
}