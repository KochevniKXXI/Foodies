package ru.requestdesign.test.nomad.core.data.repository

import ru.requestdesign.test.nomad.core.model.Tag

/**
 * Интерфейс для работы с [Tag].
 */
interface TagsRepository {
    suspend fun getTags(): Result<List<Tag>>
}