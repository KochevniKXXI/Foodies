package ru.requestdesign.test.nomad.core.data.repository

import ru.requestdesign.test.nomad.core.model.Tag

interface TagsRepository {
    suspend fun getTags(): List<Tag>
}