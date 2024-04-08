package ru.requestdesign.test.nomad.core.data.repository

import ru.requestdesign.test.nomad.core.model.Category

/**
 * Интерфейс для работы с [Category].
 */
interface CategoriesRepository {
    suspend fun getCategories(): Result<List<Category>>
}